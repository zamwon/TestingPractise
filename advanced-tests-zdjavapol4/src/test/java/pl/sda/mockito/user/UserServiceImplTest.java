package pl.sda.mockito.user;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;


import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final long IDENTIFIER = 1L;

    private static final User USER = new User(IDENTIFIER, "Jan", "Kowalski");

    private static final User USERNOTVALID = new User();


    @Mock
    private UserRepository userRepository;

    @Mock
    private UserValidator userValidator;

    @InjectMocks
    private UserServiceImpl userService;

    //powyższy kod robi to samo co poniżej zakomentowany

//    @BeforeEach
//    public void before(){
//        userService = new UserServiceImpl(userRepository, userValidator);
//    }

    @Test
    public void shouldGetUserById() {


        //given

        when(userRepository.findById(IDENTIFIER)).thenReturn(Optional.of(USER));


        //when

        User actualUser = userService.getUserById(IDENTIFIER);

        //then

        Assertions.assertThat(USER).isEqualTo(actualUser);

        verify(userRepository).findById(IDENTIFIER);
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(userValidator);

    }

    @Test
    public void shouldThrowNoSuchElementExceptionWhenUserDoesNotExists() {

        //given
        when(userRepository.findById(IDENTIFIER)).thenReturn(Optional.empty());

        //when
        Assertions
                .assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> userService.getUserById(IDENTIFIER));

        //then
        verify(userRepository).findById(IDENTIFIER);
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(userValidator);

    }
    @Test
    public void shouldCreateUser(){

        //given

        when(userValidator.isUserValid(USER)).thenReturn(true);
        when(userRepository.addUser(USER)).thenReturn(USER);

        //when

        User actualUser = userService.createUser(USER);

        //then

        Assertions.assertThat(USER).isEqualTo(actualUser);
        verify(userValidator).isUserValid(USER);
        verifyNoMoreInteractions(userValidator);
        verify(userRepository).addUser(USER);
        verifyNoMoreInteractions(userRepository);
    }
    //todo
    @Test
    public void shouldNotCreateUser(){
        //given

        when(userValidator.isUserValid(USERNOTVALID)).thenReturn(false);

        //when

        Assertions
                .assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> userService.createUser(USERNOTVALID));

        //then

        verify(userValidator).isUserValid(USERNOTVALID);
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(userValidator);

    }
}