/// <reference path="../typings/globals/jquery/index.d.ts" />

function toggleSidebar() {
    const sidebar = jQuery('#sidebar-container');
    sidebar.toggleClass('d-lg-block');
};

function removeHorizontalScrollbar() {
    if (jQuery(window).width() > 1280) {
        jQuery('.audio-list-block.table-responsive').css('overflow-x', 'hidden');
        console.log('Horizontal scrollbar hidden');
    };
};

function getNodeMouseOffset(node, e) {
    let parentOffset = node.parent().offset();
    let offsetX = e.pageX - parentOffset.left;
    let offsetY = e.pageY - parentOffset.top;

    let result = {
        offsetX: offsetX,
        offsetY: offsetY
    };

    return result;
};

function formatTime(seconds) {
    return new Date(seconds * 1000).toISOString().substr(11, 8);
}

function handleAudioPlayerTimelineClick() {
    const timeline = jQuery('.audio-player-timeline');

    timeline.on('click', (e) => {
        let width = timeline.width();
        let mouseOffsetX = Math.abs(Math.ceil(getNodeMouseOffset(timeline, e).offsetX - 15));
        let percent = (mouseOffsetX * 100 / width) + '%';
        
        jQuery('.timeline-current-stripe').css('width', percent);
        jQuery('.timeline-current-thumb').css('left', percent);
    });
};

function updateDateInputRange() {
    let today = new Date().toISOString().split('T')[0];
    jQuery('#inputCreationDate').attr('max', today);
};

function updateRangeGradient(slider, rangeValue) {
    const percentage = (rangeValue - slider.attr('min')) / (slider.attr('max') - slider.attr('min')) * 100;
    slider.css('background-image', `linear-gradient(90deg, #00990d ${percentage}%, transparent ${percentage}%)`);
}

var playAgain = false;
function updateRangeBackground(id) {
    var slider = jQuery('#' + id);
    updateRangeGradient(slider, slider.val());
    slider.on('input', (e) => {
        updateRangeGradient(slider, slider.val());
    });
}

function addEventListenerForVolumeChange() {
    var slider = jQuery('#volumeRange');
    slider.on('input', (e) => {
        getAudioContainer().volume = slider.val();
    });
}

function handleAudioActionButton(audioElement) {
    if (audioElement.paused) {
        audioElement.play();
    } else {
        audioElement.pause();
    }
}

function getAudioContainer() {
    return jQuery('#audioContainer')[0];
}

function addEventHandlerForAudioActionButton(id, idOther) {
    var audioElement = getAudioContainer();
    var button = jQuery('#' + id);
    var buttonOther = jQuery('#' + idOther);
    var url = 'http://localhost:8080/WavyArch/files/audios/audio-mpeg_a5ed2246-8cec-4366-92c1-1731777882f5';
    loadAudio(url);
    button.on('click', (e) => {
        handleAudioActionButton(audioElement);
        button.toggleClass('d-none');
        buttonOther.toggleClass('d-none');
    });
}

function addEventListenerForAudioTimeUpdate() {
    var slider = jQuery('#progressRange');
    var audioElement = getAudioContainer();
    var currentTimeElement = jQuery('#currentTime');
    audioElement.ontimeupdate = (e) => {
        if (audioElement.duration) {
            slider.val(slider.attr('max') * audioElement.currentTime / audioElement.duration);
        } else {
            slider.val(0);
        }
        updateRangeGradient(slider, slider.val());

        if (audioElement.duration === audioElement.currentTime) {
            audioElement.pause();
            jQuery('#playButton').removeClass('d-none');
            jQuery('#pauseButton').addClass('d-none');
        }

        currentTimeElement.html(formatTime(Math.floor(audioElement.currentTime)));
    };
}

function addEventListenerForAudioTimelineChange() {
    var slider = jQuery('#progressRange');
    var audioElement = getAudioContainer();

    slider.on('change', (e) => {
        var newTime = Number(slider.val() * audioElement.duration / slider.attr('max'));

        audioElement.currentTime = newTime;
        if (playAgain) {
            audioElement.play();
            playAgain = false;
        }
    });

    slider.on('input', (e) =>{
        if (!audioElement.paused) {
            audioElement.pause();
            playAgain = true;
        }
    });
}

function loadAudio(url) {
    var audioElement = getAudioContainer();
    audioElement.src = url;
    audioElement.load();
    
    audioElement.onloadedmetadata = (e) => {
        var durationElement = jQuery('#duration');
        durationElement.html(formatTime(Math.floor(audioElement.duration)));
    };
}

jQuery('#sidebar-toggle-btn').on('click', () => {
    toggleSidebar();
});

jQuery((e) => {
    removeHorizontalScrollbar();
    updateDateInputRange();
    updateRangeBackground('volumeRange');
    updateRangeBackground('progressRange');
    addEventHandlerForAudioActionButton('playButton', 'pauseButton');
    addEventHandlerForAudioActionButton('pauseButton', 'playButton');
    addEventListenerForAudioTimeUpdate();
    addEventListenerForVolumeChange();
    addEventListenerForAudioTimelineChange();
});