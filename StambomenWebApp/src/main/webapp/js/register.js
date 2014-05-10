//if authCode fill hidden field fbLoginAuthCode and submit form
function fbRegister() {
    fbRegisterAuthCode(function(authCode) {
        if (authCode) {
            $('[name=fbRegisterAuthCode]').val(authCode);
            $('[name=registerForm]').submit();
        }
    });
}