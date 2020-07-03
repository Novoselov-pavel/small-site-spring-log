/**
 * Модуль получения/отправки данных Json *
 *
 */
function postForm(window, url){
    $(window.document).ready(
        function (window) {
            $("#breed").eq(0).prop("disabled",false);
            let dog = {
                name:$("#dogName").eq(0).val(),
                breed: $("#breed").eq(0).val()
            };
            let json = JSON.stringify(dog);
            let xhr = new XMLHttpRequest();
            xhr.open("POST",url,true);
            xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    let receiveObject = JSON.parse(xhr.responseText);
                    fillForm(window,receiveObject);
                }
            };
            xhr.send(json);
            loadForm(window,url);
        }
    );
}

function loadForm(window, url){
    $(window.document).ready(
        $.getJSON(url, function (data) {
            if (typeof data.name !== 'undefined') {
                let dog = JSON.parse(data);
                fillForm(window, dog);
            }
        })
    )
}

function fillForm(window, dog){
    if (typeof dog.name !== 'undefined') {
        let dogElement = $("#dogName").eq(0);
        let breedElement = $("#breed").eq(0);
        dogElement.val(dog.name);
        breedElement.val(dog.breed);
        dogElement.prop("readonly","readonly")
        breedElement.prop("disabled","disabled");
    }
}