package pl.sda.mockito.message;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sda.mockito.exception.SdaException;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrivateMessageSenderTest {


    private static final String TEXT = "Hello";
    private static final String AUTHOR_ID = "Andrzej";
    private static final String RECIPIENT_ID = "Anna";

    private static final Message MESSAGEINVALID = null;

    @Mock
    private MessageProvider messageProvider;

    @Mock
    private MessageValidator messageValidator;

    @InjectMocks
    private PrivateMessageSender privateMessageSender;


    @Captor
    private ArgumentCaptor<Message> messageCaptor;

    @Test
    public void shouldSendPrivateMessage() {

        //given

        when(messageValidator.isMessageValid(any())).thenReturn(true);
        when(messageValidator.isMessageRecipientReachable(RECIPIENT_ID)).thenReturn(true);

        doNothing().when(messageProvider).send(any(), eq(MessageType.PRIVATE));


        //when

        privateMessageSender.sendPrivateMessage(TEXT, AUTHOR_ID, RECIPIENT_ID);

        //then

        verify(messageValidator).isMessageValid(any());
        verify(messageValidator).isMessageRecipientReachable(RECIPIENT_ID);
        verifyNoMoreInteractions(messageValidator);
        verify(messageProvider).send(any(), eq(MessageType.PRIVATE));
        verifyNoMoreInteractions(messageProvider);
    }


    @Test
    public void shouldSendPrivateMessageWithArgumentCaptor() {

        //given

        when(messageValidator.isMessageValid(any())).thenReturn(true);
        when(messageValidator.isMessageRecipientReachable(RECIPIENT_ID)).thenReturn(true);

        doNothing().when(messageProvider).send(any(), eq(MessageType.PRIVATE));


        //when

        privateMessageSender.sendPrivateMessage(TEXT, AUTHOR_ID, RECIPIENT_ID);

        //then

        verify(messageValidator).isMessageValid(messageCaptor.capture());
        verify(messageProvider).send(messageCaptor.capture(), eq(MessageType.PRIVATE));
        verify(messageValidator).isMessageRecipientReachable(RECIPIENT_ID);
        verifyNoMoreInteractions(messageValidator);
        verifyNoMoreInteractions(messageProvider);

        List<Message> messageCaptorAllValues = messageCaptor.getAllValues();

        Assertions.assertEquals(2, messageCaptorAllValues.size());
        for (Message message : messageCaptorAllValues) {
            Assertions.assertEquals(AUTHOR_ID, message.getAuthor());
            Assertions.assertEquals(RECIPIENT_ID, message.getRecipient());
            Assertions.assertEquals(TEXT, message.getValue());
            Assertions.assertNotNull(message.getId());
            Assertions.assertNotNull(message.getSendAt());
        }
    }

    @Test
//todo
    public void shouldNotSendMessageWhenMessageIsInvalidAndRecipientReachable(){

        //given
        when(messageValidator.isMessageValid(any())).thenReturn(false);

        //when

        Assertions
                .assertThrows(SdaException.class, () -> privateMessageSender.sendPrivateMessage(TEXT,AUTHOR_ID,RECIPIENT_ID));

        //then


    }
    @Test
//todo
    public void shouldNotSendMessageWhenMessageIsValidAndRecipientIsNotReachable(){

        //given
        when(messageValidator.isMessageValid(any())).thenReturn(true);
        when(messageValidator.isMessageRecipientReachable(RECIPIENT_ID)).thenReturn(false);

        //when

        Assertions
                .assertThrows(SdaException.class, () -> privateMessageSender.sendPrivateMessage(TEXT,AUTHOR_ID,RECIPIENT_ID));

        //then


    }
}