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

            var currentIndex = Number(jQuery('.audio-current-id').html().replace('audio-record-', ''));
            if (currentIndex != audioList.length) {
                jQuery('#nextButton').trigger('click');
            }
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

var audioList = [];
function initAudioPlaylist() {
    var tracksCount = Number(jQuery('.audio-list-size').html().replace('tracks', '').trim());

    for (var i = 1; i <= tracksCount; i++) {
        var id = 'audio-record-' + i;
        audioList.push({
            id: id,
            info: getAudioInfoById(id)
        });
    }
}

function findLoadedAudioInfoById(id) {
    for (var infoObj of audioList) {
        if (infoObj.id === id) {
            return infoObj;
        }
    }
    return null;
}

function addEventListenerForPlayChoosedButton() {
    var playChoosedButtons = jQuery('.playChoosedButton');
    var audioElement = getAudioContainer(); 
    playChoosedButtons.on('click', (e) =>{
        var id = e.target.children[0].innerHTML;
        
        var playerBlock = jQuery('.audio-player');
        if (playerBlock.hasClass('d-none')) {
            playerBlock.removeClass('d-none');
        }

        if (!audioElement.paused) {
            audioElement.pause();
        }

        var infoObj = findLoadedAudioInfoById(id);
        loadAudio(infoObj.info.link);
        audioElement.play();
        
        jQuery('.audio-player-title').html(infoObj.info.name);
        jQuery('.audio-player-performer').html(infoObj.info.performers);
        jQuery('.audio-current-id').html(infoObj.id);
        jQuery('#playButton').addClass('d-none');
        jQuery('#pauseButton').removeClass('d-none');
    });
}

function addEventHandlerForNextButton() {
    var button = jQuery('#nextButton');
    var audioElement = getAudioContainer(); 
    
    button.on('click', (e) => {
        var currentId = jQuery('.audio-current-id').html();
        var currentIndex = Number(currentId.replace('audio-record-', ''));
    
        var newIndex = currentIndex + 1;
        if (newIndex > audioList.length) {
            newIndex = 1;
        }
    
        if (!audioElement.paused) {
            audioElement.pause();
        }

        var infoObj = findLoadedAudioInfoById('audio-record-' + newIndex);
        loadAudio(infoObj.info.link);
        audioElement.play();
        
        jQuery('.audio-player-title').html(infoObj.info.name);
        jQuery('.audio-player-performer').html(infoObj.info.performers);
        jQuery('.audio-current-id').html(infoObj.id);
        jQuery('#playButton').addClass('d-none');
        jQuery('#pauseButton').removeClass('d-none');
    });  
}

function addEventHandlerForPreviousButton() {
    var button = jQuery('#previousButton');
    var audioElement = getAudioContainer(); 
    
    button.on('click', (e) => {
        var currentId = jQuery('.audio-current-id').html();
        var currentIndex = Number(currentId.replace('audio-record-', ''));
    
        var newIndex = currentIndex - 1;
        if (newIndex < 1) {
            newIndex = audioList.length;
        }
    
        if (!audioElement.paused) {
            audioElement.pause();
        }

        var infoObj = findLoadedAudioInfoById('audio-record-' + newIndex);
        loadAudio(infoObj.info.link);
        audioElement.play();
        
        jQuery('.audio-player-title').html(infoObj.info.name);
        jQuery('.audio-player-performer').html(infoObj.info.performers);
        jQuery('.audio-current-id').html(infoObj.id);
        jQuery('#playButton').addClass('d-none');
        jQuery('#pauseButton').removeClass('d-none');
    });  
}

function getAudioInfoById(id) {
    var rowSelector = '#' + id;
    var audioName = jQuery(rowSelector + ' > ' + '#audio-name').html().trim();
    var audioPerformers = jQuery(rowSelector + ' > ' + '#audio-performers' + ' > ' + '#audio-performer');
    var audioPerformersStr = jQuery.map(audioPerformers, (performer) =>{
        return performer.innerHTML;
    }).join(', ');
    var audioLink = jQuery(rowSelector).find('#audio-link').attr('href');

    var infoObj = {
        name: audioName,
        performers: audioPerformersStr,
        link: audioLink
    };

    return infoObj;
}

jQuery('#sidebar-toggle-btn').on('click', () => {
    toggleSidebar();
});

jQuery((e) => {
    removeHorizontalScrollbar();
    updateDateInputRange();

    if (jQuery('.audio-player')[0]) {
        updateRangeBackground('volumeRange');
        updateRangeBackground('progressRange');
        addEventHandlerForAudioActionButton('playButton', 'pauseButton');
        addEventHandlerForAudioActionButton('pauseButton', 'playButton');
        addEventListenerForAudioTimeUpdate();
        addEventListenerForVolumeChange();
        addEventListenerForAudioTimelineChange();
        addEventListenerForPlayChoosedButton();
        addEventHandlerForNextButton();
        addEventHandlerForPreviousButton();

        initAudioPlaylist();
    }
});