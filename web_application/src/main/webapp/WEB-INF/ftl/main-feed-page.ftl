<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="error" type="java.util.Optional<String>" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${name}(@${nickname})</title>
    <link rel="stylesheet" href="/styles/styles.css" type="text/css">
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
<div id="user-info" role="navigation">
    <div id="upload-block">
        <a href="/upload">
            <button class="btn btn-default btn-sm">
                <span class="glyphicon glyphicon-upload"></span>
            </button>
        </a>
    </div>
    <div id="app-name">
        <h4>Photogramm</h4>
    </div>
    <div id="search-block">
        <input id="search" type="text"/>
        <button id="clear" class="btn btn-default btn-xs">
            <span class="glyphicon glyphicon-remove"></span>
        </button>
        <div id="search-results" class="list-group"></div>
    </div>
    <form id="logout" method="post" action="/signout">
        <button class="btn btn-default btn-xs">
            <span class="glyphicon glyphicon-log-out"></span>
        </button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
    <div id="user-block">
        <a href="u/${nickname}">${nickname}</a>
    </div>
</div>
<div id="images">
<#if images??>
    <#list images as image>
        <div class="image-container">
            <div class="image-user">
                <label name="name"><b>${image.nickname}</b></label>
            </div>
            <div class="image-pic">
                <img class="image-value" src="${image.url}">
            </div>
            <div class="image-name">
                <label>${image.name}</label>
            </div>
            <div class="image-social">
                <div class="image-social-buttons">
                    <form class="like" action="" method="post">
                        <input type="hidden" value="${image.id}" name="img-id">
                        <input type="hidden" value="${image.liked?string('true', 'false')}"
                               name="liked">
                        <button class="btn-like btn btn-link btn-md" type="submit">
                        <span class="glyphicon ${image.liked?then('glyphicon-heart', 'glyphicon-heart-empty')}"
                              name="btn-like-icon"></span>
                        </button>
                    </form>
                    <form class="comment" action="" method="">
                        <input type="hidden" value="${image.id}" name="img-id">
                        <button class="btn-comment btn btn-link btn-md" type="submit">
                            <span class="glyphicon glyphicon-comment"></span>
                        </button>
                    </form>
                </div>
                <div class="image-desc"></div>
                <div class="image-sep"></div>
                <div class="image-comment">
                </div>
            </div>
        </div>
    </#list>
</#if>
</div>
<script type="text/javascript"
        src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        let results = $('#search-results');
        let clear = $('#clear');
        let search = $('#search');

        clear.click(function () {
            results.text('');
            search.val('');
        });

        $('.like').on('submit', function (e) {
            e.preventDefault();
            let liked = $("input[name='liked']", this);
            let id = $("input[name='img-id']", this).val();
            let btn = $("span[name='btn-like-icon']", this);
            let url = liked.val() === 'true' ? '/social/dislike/' + id : '/social/like/' + id;
            $.post(url, {'${_csrf.parameterName}': "${_csrf.token}"},
                    function (data, status) {
                        if (status === 'success') {
                            btn.toggleClass('glyphicon-heart-empty');
                            btn.toggleClass('glyphicon-heart');
                            liked.val(liked.val() === 'true' ? 'false' : 'true');
                        }
                    });
        });

        search.keyup(function () {
            let txt = search.val();
            results.text('');
            if (txt === '') {
                return;
            }
            $.get("/search?q=" + txt, function (data, status) {
                for (i = 0, len = data.length; i < len; i++) {
                    let user = data[i];
                    let name = user.name;
                    let nickname = user.nickname;
                    results.append($('<a>')
                            .attr('class', 'list-group-item')
                            .attr('href', 'u/' + nickname).text(name));
                    results.append($('</br>'));
                    results.attr('visible', 'true')
                }
            });
        });
    });
</script>
</body>
</html>