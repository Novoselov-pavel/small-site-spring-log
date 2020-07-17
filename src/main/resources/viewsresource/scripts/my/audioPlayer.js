class AudioPlayer {

    constructor(playlist, loadRef) {
        this.playlist = playlist;
        this.loadRef = loadRef;
        this.audio = null;
        this.volume = 0.1;
        this.songNameElement = null;
        this.progressBar = null;
        this.progressBarId = null;
        this.loadMusicPlayer();
    }

    loadMusicPlayer() {

        const setActive = (el) => {
            this.stopMusic();
            el.preventDefault();
            $(this.playlist).find(".playlist-item").removeClass("active");
            $(el.target).addClass("active");
            this.playMusic();
        };

        const fillPlayList = (playlist, data) => {
            for (const el of data) {
                let fileName = el.fileName;
                let href = el.href;

                let item = $('<a class="dropdown-item playlist-item" href="'+href+'">'+fileName+'</a>');
                item.click(setActive);
                playlist.append(item);
            }
        };

        $.getJSON(this.loadRef, (data) => {
            if (typeof data.length !== 'undefined') {
                fillPlayList(this.playlist,data);
            }
        })



    }

    setPlayButton(button) {
        $(button).bind('click', ()=> this.playMusic());
    }

    setStopButton(button) {
        $(button).bind('click', () => this.stopMusic());
    }

    setPauseButton(button) {
        $(button).bind('click', () => this.pauseMusic());
    }

    setVolumeUpButton(button) {
        $(button).bind('click', () => this.volumeUp());
    }
    setVolumeDownButton(button) {
        $(button).bind('click', () => this.volumeDown());
    }
    setSongNameElement(a){
        this.songNameElement = a;
    }

    setTimeFromPercent(persent){
        if (this.audio === null) {
            return;
        }
        let sec = Math.round(persent*0.01*this.audio.duration);
        this.audio.currentTime = sec;
    }

    setProgressBar(progressBar) {
        this.progressBar = progressBar;
    }

    updateProgressBar() {
        if (this.progressBar===null) return;

        if (this.audio=== null) {
            $(this.progressBar).css("width","0%");
            return;
        } else {
           let percent = Math.round((this.audio.currentTime/this.audio.duration)*100) + '%';
            $(this.progressBar).css("width",percent);
        }
    }

    playMusic() {
        const getSong = ()=>{
            let song = $(this.playlist).find(".playlist-item").filter(".active");
            if (song.length === 0) {
                song = $(this.playlist).find(".playlist-item").first().addClass("active");
                return song;
            }
            return song;
        };


        if (typeof this.audio!=='undefined' && this.audio!==null) {
            this.audio.play();
        } else {
            let song = getSong();
            let ref = song.attr('href');
            this.songNameElement.text(song.text());
            let currentAudio = new Audio(ref);
            currentAudio.volume = this.volume;
            this.audio = currentAudio;
            this.progressBarId = setInterval(()=>this.updateProgressBar(),100);
            this.audio.play();
        }
    }


    stopMusic() {
        if (typeof this.audio!=='undefined' && this.audio!==null) {
            this.audio.pause();
            this.audio.currentTime = 0;
            if (this.progressBarId !==null) {
                clearInterval(this.progressBarId);
                this.progressBarId = null;
            }
            this.audio = null;
            this.updateProgressBar();
        }
    }

    pauseMusic() {
        if (typeof this.audio!=='undefined' && this.audio!==null) {
            this.audio.pause();
        }
    }

    volumeUp() {
        this.volume += 0.05;
        if (typeof this.audio!=='undefined' && this.audio!==null) {
            this.audio.volume = this.volume;
        }
    }

    volumeDown() {
        this.volume -= 0.05;
        if (typeof this.audio!=='undefined' && this.audio!==null) {
            this.audio.volume = this.volume;
        }
    }

}

