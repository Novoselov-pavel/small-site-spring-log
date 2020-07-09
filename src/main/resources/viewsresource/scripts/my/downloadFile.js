function downloadFile() {
    $(window.document).ready(function () {
            let element = $(".carousel-item").filter(".active").eq(0);
            let img = element.find(".downloadImage").eq(0);
            let url = img.attr('src');
            url += "&download=true";

            let linkElement = document.createElement("a");
            linkElement.href = url;
            linkElement.download = "true";
            document.body.appendChild(linkElement);
            linkElement.click();
            document.body.removeChild(linkElement);
            delete linkElement;
        }
    )
}