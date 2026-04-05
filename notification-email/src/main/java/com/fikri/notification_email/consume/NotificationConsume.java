package com.fikri.notification_email.consume;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fikri.notification_email.model.NotificationModel;
import com.fikri.notification_email.services.NotifcationServices;

@Service
public class NotificationConsume {

    private final NotifcationServices emailService;

    public NotificationConsume(NotifcationServices emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "orderQueue")
    public void receiveMessage(NotificationModel order) {

        System.out.println("🔥 MASUK CONSUMER");
        System.out.println("RECEIVED ORDER: " + order.getId());

        String subject = "Order Baru Masuk!";
        String body = "Tugas Arsitektur Berbasis Layanan" +
                "\nNama: Fikri Rahmadani" +
                "\nNIM: 2511089005" +
                "\n\nBerikut adalah data order yang di post melalu postman, kemudian dikirim ke RabbitMQ dan diterima oleh consumer lalu diteruskan ke email sebagai notifikasi:"
                +
                "\nOrder ID: " + order.getId() +
                "\nProduk ID: " + order.getProdukId() +
                "\nJumlah: " + order.getJumlah() +
                "\nTotal: " + order.getTotal();

        emailService.sendEmail(
                "youremail@example.com", // ganti email kamu
                subject,
                body);
    }
}