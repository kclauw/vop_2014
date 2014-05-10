//loads fbSDK asynchronously
window.fbAsyncInit = function() {
    FB.init({
        appId: '225842214289570',
        xfbml: true,
        version: 'v2.0'
    });
};

//initializes fbSDK
(function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) {
        return;
    }
    js = d.createElement(s);
    js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

//returns authCode from fbLogin
function fbLoginAuthCode(callback) {
    FB.login(function(response) {
        if (response.authResponse) {
            var accessToken = FB.getAuthResponse()['accessToken'];
            var expiresIn = FB.getAuthResponse()['expiresIn'];
            var authCode = accessToken.toString() + "&expires_in=" + expiresIn.toString();
            callback(authCode)
        }
    }, {scope: 'public_profile,email,user_location,user_hometown,user_birthday'});
}

//returns authCode from fbRegister
function fbRegisterAuthCode(callback) {
    FB.login(function(response) {
        if (response.authResponse) {
            var accessToken = FB.getAuthResponse()['accessToken'];
            var expiresIn = FB.getAuthResponse()['expiresIn'];
            var authCode = accessToken.toString() + "&expires_in=" + expiresIn.toString();
            callback(authCode)
        }
    }, {scope: 'public_profile,email,user_location,user_hometown,user_birthday'});
}