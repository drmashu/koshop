function koshop_get(url) {
    $.ajax({
        async: true,
        url: url,
        type: "get",
        success: function (doc) {
            $('#target_body').html(doc);
        }
    });
}
