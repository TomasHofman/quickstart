/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.mdb;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * <p>
 * A simple Message Driven Bean that asynchronously receives and processes the messages that are sent to the queue.
 * </p>
 *
 * @author Serge Pagop (spagop@redhat.com)
 *
 */
@MessageDriven(name = "HelloWorldQueueMDB", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/HELLOWORLDMDBQueue"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
@TransactionManagement(TransactionManagementType.BEAN)
public class HelloWorldQueueMDB implements MessageListener {

    private final static Logger LOGGER = Logger.getLogger(HelloWorldQueueMDB.class.toString());

//    @Resource(name = "java:comp/DefaultJMSConnectionFactory") // Artemis
    @Resource(lookup = "java:/activemq/ConnectionFactory") // AMQ
    private QueueConnectionFactory connectionFactory;

    @Resource
    private UserTransaction tx;

    /*@Inject
    @JMSConnectionFactory("java:/JmsXA")
    private JMSContext jmsContext;*/

    /**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message rcvMessage) {
        TextMessage msg = null;
        try {
            if (rcvMessage instanceof TextMessage) {
                msg = (TextMessage) rcvMessage;
                LOGGER.info("Received Message from queue: " + msg.getText());
            } else {
                LOGGER.warning("Message of wrong type: " + rcvMessage.getClass().getName());
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }

        try {
            Context context = new InitialContext();

            QueueConnection connection = connectionFactory.createQueueConnection();
            connection.start();

            QueueSession session = connection.createQueueSession(true, QueueSession.AUTO_ACKNOWLEDGE);
            Queue queue = (Queue) context.lookup("java:jboss/exported/jms/queue/test");
            QueueSender producer = session.createSender(queue);
            QueueReceiver consumer = session.createReceiver(queue);

            tx.begin();
            LOGGER.info("Sending message.");
            TextMessage textMessage = session.createTextMessage("text msg");
            producer.send(textMessage);
            tx.commit();

            tx.begin();
            TextMessage receivedMessage;
            do {
                receivedMessage = (TextMessage) consumer.receiveNoWait();  // throws exception here
                if (receivedMessage != null) {
                    LOGGER.info("Received message: " + receivedMessage.getText());
                } else {
                    LOGGER.info("No message received.");
                }
            } while (receivedMessage != null);
            tx.commit();

            producer.close();
            consumer.close();
            session.close();
            connection.close();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        }
    }
}
