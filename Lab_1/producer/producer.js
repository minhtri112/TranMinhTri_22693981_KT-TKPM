const express = require("express");
const amqp = require("amqplib");

const app = express();
app.use(express.json());

const RABBITMQ_URL = "amqp://user:password@rabbitmq:5672";
const QUEUE = "order_queue";
const DEAD_LETTER_QUEUE = "order_queue.dlq";


let chanel;
async function connectRabbitMQ() {
    while (true) {
        try {
            const conn = await amqp.connect(RABBITMQ_URL);
            chanel = await conn.createChannel();
            await chanel.assertQueue(QUEUE, {
                durable: true,
                deadLetterExchange: "",
                deadLetterRoutingKey: DEAD_LETTER_QUEUE
            });
            console.log("Producer connected to RabbitMQ");
            break;
        } catch (error) {
            console.log("Waiting for RabbitMQ...");
            await new Promise((r) => setTimeout(r, 3000));
        }
    }
}

app.post("/send", async (req, res) => {
    const { orderId, message } = req.body;



    if (!message) {
        return res.status(400).json({ error: "message is required" });
    }
    const data = {
        message : message,
        orderId : orderId,
        timestamp : new Date()
    }

    chanel.sendToQueue(
        QUEUE,
        Buffer.from(JSON.stringify(data)),
        {
            persistent : true
        }
    );
    console.log("Sent:", data);

    res.json({status: "sent", dataSent: data});

});


connectRabbitMQ();



app.listen(3000, () => {
    console.log("Producer APT listening on port 3000");
})