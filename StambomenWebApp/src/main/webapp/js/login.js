//if authCode fill hidden field fbLoginAuthCode and submit form
function fbLogin() {
    fbLoginAuthCode(function(authCode) {
        if (authCode) {
            $('[name=fbLoginAuthCode]').val(authCode);
            $('[name=loginForm]').submit();
        }
    });
}