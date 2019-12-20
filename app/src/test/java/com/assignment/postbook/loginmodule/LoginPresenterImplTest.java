package com.assignment.postbook.loginmodule;

import android.content.Context;

import com.assignment.postbook.R;
import com.assignment.postbook.ui.loginmodule.LoginContract;
import com.assignment.postbook.ui.loginmodule.LoginPresenterImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterImplTest {

    @Mock
    private LoginContract.View view;
    @Mock
    private Context context;
    @Mock
    private LoginPresenterImpl presenter;


    @Before
    public void setUp() throws Exception {
        presenter = new LoginPresenterImpl(view, context);
    }

    @Test
    public void showErrorMsgWhenUserIdEmpty() {
        when(view.getUserId()).thenReturn("");
        presenter.checkUserIDValidation();

        verify(view).showUserIdError(R.string.enter_userid);
    }

    @Test
    public void showUserPostActivityWhenUserIdVaid() {
        when(view.getUserId()).thenReturn("5");
        presenter.checkUserIDValidation();

        verify(view).navigationToHomePage();
    }

}