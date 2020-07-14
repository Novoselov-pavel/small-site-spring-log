function uploadFiles(url, formData, maxlength, updateUrl) {
    let appendFiles = [];
    // $(files).each(function(index, file) {
    //     if (file.size <= maxlength) {
    //         appendFiles.push(file);
    //     }
    // });
    formData.append("somedata", "somevalue")

    $.ajax({
        url: url,
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        success: function(val) {
            updateMyForm(updateUrl);
        }
    });

}