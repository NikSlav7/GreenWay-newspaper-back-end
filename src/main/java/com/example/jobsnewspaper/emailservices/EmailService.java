package com.example.jobsnewspaper.emailservices;


import com.example.jobsnewspaper.converters.TimestampConverter;
import com.example.jobsnewspaper.daos.EmailMessagesDao;
import com.example.jobsnewspaper.daos.EmailNewspaperFollowersDAO;
import com.example.jobsnewspaper.domains.*;
import com.example.jobsnewspaper.emailTemplates.EmailTemplates;
import com.example.jobsnewspaper.emailTemplates.insidecontent.InsideContent;
import com.example.jobsnewspaper.emailTemplates.insidecontent.InsideContentN1;
import com.example.jobsnewspaper.emailTemplates.mailtemplates.Template;
import com.example.jobsnewspaper.exceptions.WrongEmailDetailsException;
import com.example.jobsnewspaper.sort.Sorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;


@Component
@RequestMapping("/api/")
@RestController
public class EmailService {


    @Value("${spring.mail.username}")
    private String sendingEmail;

    @Value("${spring.mail.password}")
    private String sendingPassword;


    private final String host = "localhost";


    private final JavaMailSender javaMailSender;

    private final EmailMessagesDao emailMessagesDao;

    private final EmailNewspaperFollowersDAO emailNewspaperFollowersDAO;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, EmailMessagesDao emailMessagesDao, EmailNewspaperFollowersDAO emailNewspaperFollowersDAO) {
        this.javaMailSender = javaMailSender;
        this.emailMessagesDao = emailMessagesDao;
        this.emailNewspaperFollowersDAO = emailNewspaperFollowersDAO;
    }

    @GetMapping(path = "email/get-emails")
    public List<EmailMessage> getEmailMessages(@RequestParam(name = "isSent")boolean isSent, @RequestParam boolean isSending,  @RequestParam String date, @RequestParam int daysOffset) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        LocalDateTime instant = LocalDateTime.ofInstant(simpleDateFormat.parse(date).toInstant(), ZoneId.of("Europe/Tallinn"));
        MessageStatus status = isSent ? MessageStatus.SENT : MessageStatus.NOT_SENT;
        if (isSending) status = MessageStatus.SENDING;


        List<EmailMessage> messages;

        if (!isSending) messages = emailMessagesDao.getDayMessages(instant.atZone(ZoneId.of("Europe/Tallinn")).toInstant().toEpochMilli(), daysOffset, status.getInd());
        else messages = emailMessagesDao.getSendingMessages();

        List<EmailMessage> modifiableMessages = new ArrayList<>(messages);
        new Sorter().sortEmailMessagesByDate(modifiableMessages);
        if (isSent) Collections.reverse(modifiableMessages);
        return modifiableMessages;
    }



    @PostMapping(path = "email/remove")
    public void deleteEmailMessage(@RequestBody EmailMessageWithStringInterests emailMessage) throws WrongEmailDetailsException {
        emailMessagesDao.removeMessage(new EmailMessage().fromMessageWithStringInterests(emailMessage));
    }


    @PostMapping(path = "/email/send-test-email")
    public void sendTestEmail(@RequestBody TestEmailSendRequest request) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException, WrongEmailDetailsException {
        sendTestEmails(request.getMessage().getHeader(),
                request.getEmails().stream().map(email -> new EmailNewspaperFollower(email)).collect(Collectors.toList()),
                request.getMessage());
    }

    @GetMapping(path = "email/get-status")
    public List<String> getAllPossibleStatuses(){
        return MessageStatus.getAllStatusesNames();
    }

    @GetMapping(path = "email/get-one-email")
    public EmailMessageWithStringInterests getEmailMessage(@RequestParam String time, int status) throws WrongEmailDetailsException {
        return new EmailMessageWithStringInterests().
                fromEmailMessage(emailMessagesDao.getEmailMessage(String.valueOf(new TimestampConverter().convert(LocalDateTime.parse(time))), status));
    }

    @PostMapping(path = "email/replace-email")
    public void replaceMessage(@RequestBody ReplaceMessageRequest request) throws WrongEmailDetailsException {
        if (request.getMessageList().size() != 2) throw new WrongEmailDetailsException("Only 2 emails should be provided");

        emailMessagesDao.replaceMessage(new EmailMessage().fromMessageWithStringInterests(request.getMessageList().get(0)),
                new EmailMessage().fromMessageWithStringInterests(request.getMessageList().get(1)));
    }

    @PostMapping(path = "/email/send-now")
    public void sendMessageNow(@RequestBody EmailMessageWithStringInterests emailMessageWithStringInterests) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException, WrongEmailDetailsException {
        EmailMessage emailMessage = new EmailMessage().fromMessageWithStringInterests(emailMessageWithStringInterests);

        sendEmailsToEverybody(emailMessage.getMessageHeader(),
                emailNewspaperFollowersDAO.getAllEmailNewspaperFollowers(),
                emailMessage);
        LocalDateTime curTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Europe/London"));

        EmailMessage copy = new EmailMessage(emailMessagesDao.getEmailMessage(String.valueOf
                (new TimestampConverter().convert(emailMessage.getWhenToSend())),
                MessageStatus.SENT.getInd()));
        copy.setWhenToSend(curTime);
        emailMessagesDao.replaceMessage(emailMessage, copy);
    }


    @PostMapping(path = "email/new-email")
    public boolean postEmailToSend(@RequestBody EmailSendingRequest emailSendingRequest) throws WrongEmailDetailsException, ParseException {
        Map<Interest, String> interestMap = Interest.interestMapFromStringMap(emailSendingRequest.getBody());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        EmailMessage emailMessage = new EmailMessage(
                emailSendingRequest.getHeader(),
                emailSendingRequest.getSubHeader(),
                MessageStatus.NOT_SENT.getInd(),
                interestMap,
                LocalDateTime.ofInstant(simpleDateFormat.parse(emailSendingRequest.getWhenToPost()).toInstant(), ZoneId.of("Europe/Tallinn"))
                );
        emailMessagesDao.saveEmailMessage(emailMessage);
        return true;
    }

    public void sendMultipleMailMessages(String subject, String messageContent, List<EmailNewspaperFollower> followerList){
        new MailSenderMultithread(subject, messageContent, followerList, javaMailSender).run();
    }


    public void sendEmailsToEverybody(String subject, List<EmailNewspaperFollower> followers, EmailMessage messageToSend) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        emailMessagesDao.changeEmailStatus(messageToSend, MessageStatus.SENDING);
        EmailTemplates templates = new EmailTemplates();
        for (EmailNewspaperFollower follower : followers){
            Template template = templates.getRandomTemplate();
            template.setTemplateHeader(messageToSend.getMessageHeader());
            template.setTemplateSubHeader(messageToSend.getMessageSubHeader());
            List<Interest> interests = follower.getInterests();
            for(int i = 0; i < 3; i++){
                InsideContent content = interests.size() <= i || interests.get(i) == null ? null :
                        new InsideContentN1().insertHeaderAndText(interests.get(i).getInterestName(), messageToSend.getBody().get(interests.get(i)));
                template.insertInsideContent(i, content);
                template.setTemplateHeader(messageToSend.getMessageHeader());
                template.setTemplateSubHeader(messageToSend.getMessageSubHeader());
            }
            sendMultipleMailMessages(subject, template.getTemplateContent(), List.of(follower));

        }
        emailMessagesDao.changeEmailStatus(messageToSend, MessageStatus.SENT);
    }

    public void sendTestEmails(String subject, List<EmailNewspaperFollower> followers, TestMessage messageToSend) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException, WrongEmailDetailsException {
        Map<Interest, String> interestStringMap = Interest.interestMapFromStringMap(messageToSend.getInterests());
        EmailTemplates templates = new EmailTemplates();
        for (EmailNewspaperFollower follower : followers){
            Template template = templates.getRandomTemplate();
            Interest interests[] = Interest.values();
            for(int i = 0; i < interests.length; i++){
                InsideContent content = interests[i] == null ? null :
                        new InsideContentN1().insertHeaderAndText(interests[i].getInterestName(), interestStringMap.get(interests[i]));
                template.insertInsideContent(i, content);
                template.setTemplateHeader(messageToSend.getHeader());
                template.setTemplateSubHeader(messageToSend.getSubheader());
            }
            sendMultipleMailMessages(subject, template.getTemplateContent(), List.of(follower));

        }
    }



}
