function updateMyForm(url) {
    $(window.document).ready(function () {
            loadForm(url)
        }
    );
}

function loadForm(url){
    $(window.document).ready(
        $.getJSON(url, function (data) {
            if (typeof data.length !== 'undefined') {
                fillForm(data);
            }
        })
    )
}

function fillForm(data) {
    let items = $(".file-items").remove();
    let tableBody = $(".table-body-for-insert").eq(0);

    for (let element of data) {
        let number = data.indexOf(element)+1;
        let fileName = element.fileName;
        let size = element.lengthAtKb;
        let href = element.href;
        let item = $('<tr class="file-items"><th scope="row">'+number+
            '</th><td><a href="'+href+'" download="true">'+fileName+'</a></td><td>'+size+'</td></tr>')
        tableBody.append(item);

    }
}