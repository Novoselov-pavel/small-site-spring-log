function uploadFiles(url, files, maxlength, updateUrl) {
    let data = new FormData();
    $(files).each(function(index, file) {
        if (file.size <= maxlength) {
            data.append('files', file);
        }
    });



    $.ajax({
        url: url,
        type: "POST",
        data: data,
        contentType: false,
        processData: false,
        success: function(val) {
            updateMyForm(updateUrl);
        }
    });

}