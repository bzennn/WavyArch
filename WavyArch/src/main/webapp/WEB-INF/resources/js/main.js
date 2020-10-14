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

// function parseAudioLengthString(lengthStr) {
//     let lengthData = lengthStr.split(':', 3);
    
//     let seconds = 0;
//     let minutes = 0;
//     let hours = 0;
//     let secondsAll = 0;

//     for (let i = lengthData.length; i >= 0; i--) {
//         if (i = lengthData)
//     }
// }

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

jQuery('#sidebar-toggle-btn').on('click', () => {
    toggleSidebar();
});

jQuery((e) => {
    removeHorizontalScrollbar();
    handleAudioPlayerTimelineClick();
    updateDateInputRange();
});

