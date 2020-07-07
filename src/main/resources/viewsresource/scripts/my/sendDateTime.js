function updateDate(window) {
    $(window.document).ready(function () {
         let timeText = $(".currentTime").eq(0);
         let now = new Date();
         timeText.text("Текущее время: " + now.toString());
        }
    )
}

function buttonClick() {
    $(window.document).ready(function () {
            let now = new Date();
            let dateString = now.toUTCString();
            let xhr = new XMLHttpRequest();
            let url = "/scripts/date"
            xhr.open("POST",url,true);
            xhr.setRequestHeader('Content-type', 'text/plain;charset=utf-8')
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    let receiveObject = xhr.responseText;
                }
            };
            xhr.send(dateString);
            let timeText = $(".sentTimeToServer").eq(0);
            timeText.prop("hidden", false);
            timeText.text("Отправленное на сервер время: " + now.toString());
            getDate();
        }
    )
}

function getDate() {
    $(window.document).ready(function () {
        let url = "/scripts/date"
        let xhr = new XMLHttpRequest();
        xhr.open("GET",url,true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                let receiveObject = xhr.responseText;

                ///конвертация в объект дата (отключена)
                ///let data = new Date(receiveObject);

                let timeText = $(".serverTime").eq(0);
                timeText.prop("hidden", false);
                timeText.text("Время на сервере: " + receiveObject);
            }
        };
        xhr.send();

    })
}