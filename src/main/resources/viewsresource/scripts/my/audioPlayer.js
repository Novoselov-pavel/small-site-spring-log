let audio = new Audio();
audio.src;

function loadMusicPlayer(playlist,loadRef) {
    $.getJSON(loadRef, function (data) {
        if (typeof data.length !== 'undefined') {
            fillPlayList(playlist,data);
        }
    })
}

function fillPlayList(playlist,data) {
    for (const el of data) {
        let fileName = el.fileName;
        let href = el.href;

        let item = $('<a class="dropdown-item playlist-item" href="'+href+'">'+fileName+'</a>');
        item.click(setActive);
        playlist.append(item);
        item.on('classChange',onClassChanged(el));
    }
}

function setActive(el) {
    el.preventDefault();
    //TODO
    console.log(el);
}

function onClassChanged(el) {
    //TODO
    console.log(el);
}