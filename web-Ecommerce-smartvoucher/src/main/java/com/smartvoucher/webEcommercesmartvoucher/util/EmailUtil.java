package com.smartvoucher.webEcommercesmartvoucher.util;

import com.smartvoucher.webEcommercesmartvoucher.dto.TicketDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.OrderEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WarehouseStoreEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.enums.Provider;
import com.smartvoucher.webEcommercesmartvoucher.repository.OrderRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.UserRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.WarehouseStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

@Component
public class EmailUtil {
    private final JavaMailSender javaMailSender;
    private final MailProperties mailProperties;
    private final StringsUtil stringsUtil;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final WarehouseStoreRepository warehouseStoreRepository;

    @Autowired
    public EmailUtil(final JavaMailSender javaMailSender
            , final MailProperties mailProperties,
                     final StringsUtil stringsUtil,
                     final UserRepository userRepository,
                     final OrderRepository orderRepository,
                     final WarehouseStoreRepository warehouseStoreRepository) {
        this.javaMailSender = javaMailSender;
        this.mailProperties = mailProperties;
        this.stringsUtil = stringsUtil;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.warehouseStoreRepository = warehouseStoreRepository;
    }

    public void sendVerificationEmail(String email, String verifyCode) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email xác nhận";
        String senderName = "Cổng dịch vụ đăng ký tài khoản người dùng Smartvoucher.com";
        String mailContent =
                "<img align=\"center\" border=\"0\" hspace=\"0\"" +
                        " src=\"https://lh3.google.com/u/0/d/1xAwgI8uhaZCfwQflIIgwTLnomJaPcs0k\"" +
                        " style=\"max-width:70px;margin-left:auto;display:block;margin-right:auto\" vspace=\"0\" width=\"70px\" data-bit=\"iit\">"+
                        " <h1 style=\"text-align:center\">Xin chào "+ stringsUtil.getUserNameFormDomain(email)+" </h1>" +
                        "<p align=\"center\">"+"<a style=\"padding:10px;width:104px;height:16px;display:block;margin:8;" +
                        "text-decoration:none;border:1px solid #ef5b25;text-align:center;font-size:12px;font-style:normal;font-weight:600;font-family:'Open Sans',sans-serif;color:#fff;background:#ef5b25;border-radius:5px;line-height:16px\">" +
                        verifyCode+"</a>" +"</p>"+
                        "<p align=\"center\"> Xin cảm ơn ! Cổng dịch vụ đăng ký tài khoản người dùng Smartvoucher.com";
        MimeMessage messages = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(messages);
        messageHelper.setFrom(mailProperties.getUsername(), senderName);
        messageHelper.setTo(email);
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        javaMailSender.send(messages);
    }

    public void sendResetPassword(String email, String verifyCode) throws MessagingException, UnsupportedEncodingException{
        String subject = "Đặt lại mật khẩu";
        String senderName = "Cổng dịch vụ đăng ký tài khoản người dùng Smartvoucher.com";
        String mailContent =
                "<img align=\"center\" border=\"0\" hspace=\"0\"" +
                " src=\"https://lh3.google.com/u/0/d/1xAwgI8uhaZCfwQflIIgwTLnomJaPcs0k\"" +
                " style=\"max-width:70px;margin-left:auto;display:block;margin-right:auto\" vspace=\"0\" width=\"70px\" data-bit=\"iit\">"+
                        " <h1 style=\"text-align:center\">Xin chào "+ stringsUtil.getUserNameFormDomain(email)+" </h1>" +
                        "<p align=\"center\">"+"<a style=\"padding:10px;width:104px;height:16px;display:block;margin:8;" +
                        "text-decoration:none;border:1px solid #ef5b25;text-align:center;font-size:12px;font-style:normal;font-weight:600;font-family:'Open Sans',sans-serif;color:#fff;background:#ef5b25;border-radius:5px;line-height:16px\">" +
                        verifyCode+"</a>" +"</p>"+
        "<p align=\"center\"> Xin cảm ơn ! Cổng dịch vụ đăng ký tài khoản người dùng Smartvoucher.com";
        MimeMessage messager = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(messager);
        messageHelper.setFrom(mailProperties.getUsername(), senderName);
        messageHelper.setTo(email);
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        javaMailSender.send(messager);
    }

    public void sendTicketCode(String mail, List<TicketDTO> listVoucherCode) throws MessagingException, UnsupportedEncodingException {
            UserEntity user = userRepository.findByEmailAndProvider(mail, Provider.local.name());
            List<OrderEntity> orderEntityList = orderRepository.findAllOrderByIdUser(user.getId());
            OrderEntity order = orderEntityList.get(orderEntityList.size()-1);
            WarehouseStoreEntity warehouseStore = warehouseStoreRepository.getWarehouseStoreEntitiesByIdWarehouse(order.getIdWarehouse().getId());
            String senderName = "Cổng dịch vụ thanh toán mua voucher của người dùng Smartvoucher.com";
            String subject = "Thanh toán Voucher thành công";
            StringBuilder textBuilder = new StringBuilder();
            for (int i = 0; i < listVoucherCode.size(); i++) {
            textBuilder.append("<span style='font-size: 17px; font-weight: 700;'>")
                    .append(i).append(". ")
                    .append(listVoucherCode.get(i).getIdSerialDTO().getSerialCode())
                    .append("</span> <br>");
            }
            String mailContent =
                    "<img align=\"center\" border=\"0\" hspace=\"0\"" +
                            " src=\"https://drive.google.com/uc?export=view&id=1xAwgI8uhaZCfwQflIIgwTLnomJaPcs0k\"" +
                            " style=\"max-width:70px;margin-left:auto;display:block;margin-right:auto\" vspace=\"0\" width=\"70px\" data-bit=\"iit\">"+
                    "<div style=\"color: rgb(49, 49, 49); font-family: arial, helvetica, sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; text-align: center; line-height: 24px;\"><span style=\"font-size: 18px; font-weight: bold;\">Xin chào "+stringsUtil.getUserNameFormDomain(mail)+"</span><br>\bXin cảm ơn đã mua voucher tại Smartvoucher.com<br><br><span style=\"font-size: 35px; line-height: 40px;\"><strong>INVOICE ID: "+ UUID.randomUUID()
                    .toString().substring(0, 11).replace("-", "").toUpperCase()+
        "<br></strong></span></div>\n" +
                    "<div style=\"font-family: arial, helvetica, sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; color: rgb(178, 178, 178); line-height: 21px; padding: 5px 0px;\"><strong>THÔNG TIN ĐƠN HÀNG:</strong></div>\n" +
                    "<table style=\"color: rgb(49, 49, 49); font-family: arial, helvetica, sans-serif; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; width: 540px; overflow-wrap: break-word; word-break: break-word; font-size: 16px; line-height: 24px; border-spacing: 0px; margin-bottom: 20px; border-top: 1px solid rgb(226, 227, 228);\">\n" +
                    "    <thead>\n" +
                    "        <tr>\n" +
                    "            <th style=\"height: 1px; width: 268px;\"><br></th>\n" +
                    "            <th style=\"height: 1px; width: 268px;\"><br></th>\n" +
                    "        </tr>\n" +
                    "    </thead>\n" +
                    "    <tbody>\n" +
                    "        <tr>\n" +
                    "            <td style=\"margin: 0px; padding-top: 15px;\"><strong>Order No:</strong></td>\n" +
                    "            <td style=\"margin: 0px; padding-top: 15px;\"><strong>Bill To:</strong></td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td style=\"margin: 0px;\">"+order.getOrderNo()+"</td>\n" +
                    "            <td style=\"margin: 0px;\"><a href=\"mailto:"+mail+"\" target=\"_blank\" style=\"color: rgb(17, 85, 204);\">"+mail+"</a></td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td style=\"margin: 0px; padding-top: 15px;\"><strong>Order Date:</strong></td>\n" +
                    "            <td style=\"margin: 0px; padding-top: 15px;\"><strong>Source:</strong></td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td style=\"margin: 0px;\">"+order.getCreatedAt()+"</td>\n" +
                    "            <td style=\"margin: 0px;\">smartvoucher.com</td>\n" +
                    "        </tr>\n" +
                    "    </tbody>\n" +
                    "</table>\n" +
                    "<div style=\"font-family: arial, helvetica, sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; color: rgb(178, 178, 178); line-height: 21px; padding: 5px 0px;\"><strong>ĐƠN HÀNG CỦA BẠN GỒM:</strong></div>\n" +
                    "<table style=\"color: rgb(49, 49, 49); font-family: arial, helvetica, sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; width: 540px; border-spacing: 0px; margin-bottom: 20px; border-top: 1px solid rgb(226, 227, 228);\">\n" +
                    "    <thead>\n" +
                    "        <tr style=\"background-color: rgb(241, 241, 241);\">\n" +
                    "            <th style=\"text-align: left; min-width: 100px; padding: 10px 0px 10px 10px; vertical-align: top;\">Description:</th>\n" +
                    "            <th style=\"text-align: left; min-width: 80px; padding: 10px 0px 10px 10px; vertical-align: top;\">Publisher:</th>\n" +
                    "            <th style=\"text-align: right; padding: 10px 10px 10px 0px; vertical-align: top;\">Price:</th>\n" +
                    "        </tr>\n" +
                    "    </thead>\n" +
                    "    <tbody>\n" +
                    "        <tr>\n" +
                    "            <td style=\"margin: 0px; padding-top: 15px; padding-left: 10px; word-break: break-all;\">"+warehouseStore.getIdWarehouse().getName()+"</td>\n" +
                    "            <td style=\"margin: 0px; padding-top: 15px; padding-left: 10px; word-break: break-all;\">"+warehouseStore.getIdStore().getName()+"</td>\n" +
                    "            <td style=\"margin: 0px; text-align: right; padding-top: 15px; padding-right: 10px; word-break: break-all;\">₫"+warehouseStore.getIdWarehouse().getPrice()+"&nbsp;VND</td>\n" +
                    "        </tr>\n" +
                    "    </tbody>\n" +
                    "</table>\n" +
                    "<table style=\"color: rgb(49, 49, 49); font-family: arial, helvetica, sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; width: 540px; border-spacing: 0px; margin-bottom: 20px; border-top: 1px solid rgb(226, 227, 228); table-layout: fixed; word-break: break-all;\">\n" +
                    "    <thead>\n" +
                    "        <tr style=\"background-color: rgb(241, 241, 241); line-height: 40px;\">\n" +
                    "            <th style=\"text-align: left; min-width: 120px; padding-left: 10px;\">Item:</th>\n" +
                    "            <th style=\"text-align: right; padding-right: 10px;\"><br></th>\n" +
                    "        </tr>\n" +
                    "    </thead>\n" +
                    "    <tbody>\n" +
                    "        <tr>\n" +
                    "            <td style=\"margin: 0px; padding-top: 15px; padding-left: 10px;\">"+textBuilder+"</td>\n" +
                    "            <td style=\"margin: 0px; text-align: right; padding-top: 15px; padding-right: 10px;\"></td>\n" +
                    "        </tr>\n" +
                    "    </tbody>\n" +
                    "</table>"
                    +
                    "<table style=\"color: rgb(49, 49, 49); font-family: arial, helvetica, sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; width: 540px; border-spacing: 0px; border-top: 1px solid rgb(226, 227, 228); margin-bottom: 20px; text-align: right; line-height: 26px;\">\n" +
                    "    <thead>\n" +
                    "        <tr>\n" +
                    "            <th><br></th>\n" +
                    "            <th style=\"width: 66.4219px;\"><br></th>\n" +
                    "        </tr>\n" +
                    "    </thead>\n" +
                    "    <tbody>\n" +
                    "        <tr>\n" +
                    "            <td style=\"margin: 0px; padding-top: 15px;\"><span style=\"font-weight: bold; text-transform: uppercase; color: rgb(178, 178, 178);\">TOTAL:</span></td>\n" +
                    "            <td style=\"margin: 0px; padding: 15px 10px 0px;\"><span style=\"font-weight: bold;\">₫"+warehouseStore.getIdWarehouse().getPrice()*order.getQuantity()+"&nbsp;VND</span></td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td colspan=\"2\" style=\"margin: 0px; text-align: center; padding-top: 15px;\"><br></td>\n" +
                    "        </tr>\n" +
                    "    </tbody>\n" +
                    "</table>\n" +
                    "<table style=\"color: rgb(49, 49, 49); font-family: arial, helvetica, sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; width: 540px; border-spacing: 0px; margin-bottom: 20px; border-top: 1px solid rgb(226, 227, 228); line-height: 26px;\">\n" +
                    "    <thead>\n" +
                    "        <tr>\n" +
                    "            <th><br></th>\n" +
                    "        </tr>\n" +
                    "    </thead>\n" +
                    "    <tbody>\n" +
                    "        <tr>\n" +
                    "            <td style=\"margin: 0px; text-align: center; padding-top: 15px;\">Xin cảm ơn ! Cổng dịch vụ đăng ký tài khoản người dùng Smartvoucher.com</td>\n" +
                    "        </tr>\n" +
                    "    </tbody>\n" +
                    "</table>";
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(mailProperties.getUsername(), senderName);
            messageHelper.setTo(mail);
            messageHelper.setSubject(subject);
            messageHelper.setText(mailContent, true);
            javaMailSender.send(message);
    }
}
