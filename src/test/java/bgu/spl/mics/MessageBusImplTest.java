package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.services.C3POMicroservice;
import bgu.spl.mics.application.services.HanSoloMicroservice;
import bgu.spl.mics.application.services.R2D2Microservice;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MessageBusImplTest {

    private MessageBusImpl bus;

    @Before
    public void setUp() throws Exception {
         bus=MessageBusImpl.getMessageBus();
    }
    @After
    public void tearDown() throws Exception {
        bus=null;
    }
    @Test
    public void subscribeEvent() {
       //----
    }

    @Test
    public void subscribeBroadcast() {
     //----
    }

    @Test
    public void complete() throws InterruptedException {
        MicroService m1=new HanSoloMicroservice();
        bus.register(m1);
        MicroService m2= new R2D2Microservice(1);
        bus.register(m2);
        DeactivationEvent deactivationEvent=new DeactivationEvent();
        m1.subscribeEvent(DeactivationEvent.class,(DeactivationEvent)->{});
        m2.subscribeEvent(DeactivationEvent.class,(DeactivationEvent)->{});
        Future<String> future=m1.sendEvent(deactivationEvent);//hanSolo sends R2D2 a deactivation event while creating a Future object
        //once R2D2 handles this event we would like to check if the future object has been resolved correctly using the complete method.
        m2.complete(deactivationEvent,true);//this method calls messageBusImpl complete method
        assertEquals(future.get(),true);
    }

    @Test
    public void sendBroadcast() {
        MicroService m1=new HanSoloMicroservice();
        bus.register(m1);
        MicroService m2= new C3POMicroservice();
        bus.register(m2);
        //Broadcast b=new RelaxBroadcast();
        //RelaxCall Call= new RelaxCall();
        //m1.subscribeBroadcast((Class)b.getClass(),Call);
        //m2.subscribeBroadcast((Class)b.getClass(),Call);
        // above methods call subscribeBroadcast in  Message-Bus with the same
        // classes type and the relevant microservice.
        //bus.sendBroadcast(b);
        // Ive sended an broadcast to Han Solo m1 and C3PO m2, now I want to check if they got it.
        // so, I want to check if the matching callback function has been activated with the broadcast b
        //assertEquals(Call.ReturnRelaxCall((RelaxBroadcast)bus.awaitMessage(m1)),"Relax!");
        //assertEquals(Call.ReturnRelaxCall((RelaxBroadcast) bus.awaitMessage(m2)),"Relax!");
    }

    @Test
    public void sendEvent() {
        MicroService m1=new HanSoloMicroservice();
        bus.register(m1);
        ArrayList<Integer> serialsForAttack= new ArrayList<>();
        serialsForAttack.add(2);
        serialsForAttack.add(3);
        int duration=5;
        Attack info=new Attack(serialsForAttack,duration);
        AttackEvent a = new AttackEvent(info);
        //AttackCall Call= new AttackCall ();
        //m1.subscribeEvent((Class)a.getClass(),Call);
        // above method call subscribeEvent in Message-Bus with the same
        // class type and the relevant microservice.
        m1.subscribeEvent(AttackEvent.class,(AttackEvent)->System.out.println ("Attack!"));
        m1.sendEvent(a);
        // Ive sended an event to HanSolo m1, now I want to check if he got it.
        // so, I want to check if the call function has been activated with the event a
        try {
            Message current= bus.awaitMessage(m1);
            assertTrue(current!=null);
        } catch (InterruptedException e) { }
    }

    @Test
    public void register() {
        //----
    }

    @Test
    public void unregister() {
        //----
    }

    @Test
    public void awaitMessage() {
        //test with the assumption the Q is not empty
        MicroService m1=new HanSoloMicroservice();
        bus.register(m1);
        ArrayList<Integer> serialsForAttack= new ArrayList<>();
        serialsForAttack.add(2);
        serialsForAttack.add(3);
        int duration=5;
        Attack info=new Attack(serialsForAttack,duration);
        AttackEvent a = new AttackEvent(info);
        //Callback <AttackEvent> Call= new AttackCall();
        //m1.subscribeEvent((Class)a.getClass(),(Call));
        bus.sendEvent(a);
        // Ive sended an event to the queue of m1.
        // now I check if the next message in the queue of m1 is a
        //assertEquals(bus.awaitMessage(m1),a);
    }
}