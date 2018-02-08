<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="error" type="java.util.Optional<String>" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${name}(@${nickname})</title>

    <link href="/styles/user_styles.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
<article class="article">
    <div class="media header">
        <div class="media-left avatar-container">
        </div>
        <div class="media-body details-container">
            <div class="profile-block">
                <h5 title="andre_d_minuff"><b>${nickname}</b></h5>
                <!-- If user requested information about himself - add possibility to change
                this. Otherwise check if user subscribed on this user and when no - show
                subscribe button.-->
                <div class="edit-profile">
                <#if self == true>
                    <a href="/${nickname}/edit">
                        <button class="btn btn-default btn-xs">
                            <span class="glyphicon glyphicon-cog"></span>
                        </button>
                    </a>
                <#else>
                    <button id="subscribe" class="btn btn-xs btn-info">
                        <#if subscribed>Unsubscribe<#else>Subscribe</#if>
                    </button>
                </#if>
                </div>
            </div>
            <ul class="subcribers-block">
                <li><#if images??>${images?size}</#if> publications</li>
                <li><a id="followers" href="/u/${nickname}/followers/">${subscribers}
                    subscribers</a></li>
                <li><a id="following" href="/u/${nickname}/following/">Subscribe:
                ${subscriptions}</a></li>
            </ul>
            <div><h4><b>${name}</h4></div>
        </div>
    </div>
    <div class="images">
    <#if images??>
        <#list images as image>
            <div class="image-container">
                <a href="/publication/${image.id}/">
                    <img src="${image.url}" class="image-pic">
                </a>
            </div>
        </#list>
    </#if>
    </div>
</article>
<script type="text/javascript"
        src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var subscribed = ${subscribed?c};
        var followers = ${subscribers};
        let btn = $('#subscribe');
        let ref = $('#followers');
        btn.click(function () {
            let url = subscribed ? '/s/unsubscribe' : '/s/subscribe';
            $.post(url, {
                        '${_csrf.parameterName}': "${_csrf.token}",
                        nickname: "${nickname}"
                    },
                    function (data, status) {
                        if (status != 'success') {
                            return;
                        }
                        subscribed = !subscribed;
                        followers = subscribed ? followers + 1 : followers - 1;
                        ref.text(followers + ' subscribers');
                        btn.text(subscribed ? 'Unsubscribe' : 'Subscribe');
                    });
        });
    });
</script>
</body>
</html>