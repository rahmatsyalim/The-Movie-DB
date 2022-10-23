const iframeApi = "https://www.youtube.com/iframe_api";

const localIframeApi =
  "file:///android_asset/youtube_player/iframe_api/iframe_api.js";

let videoId;

//  Async loads IFrame Player API code
function loadYouTubeIframeAPI(id) {
  videoId = id;
  var tag = document.createElement("script");
  tag.id = "youtube-iframe";
  tag.src = iframeApi;
  var firstScriptTag = document.getElementsByTagName("script")[0];
  firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
}

let player;
function onYouTubeIframeAPIReady() {
  var params = {
    height: "360",
    width: "640",
    videoId: videoId,
    playerVars: {
      playsinline: 1, // plays video inline
      fs: 1, // fullscreen button visibility
      modestbranding: 1, // youtube logo hide
    },
    events: {
      onReady: onPlayerReady,
      // onStateChange: onPlayerStateChange,
    },
  };
  player = new YT.Player("target", params);
}
function onPlayerReady(event) {
  AndroidInterface.stopLoading();
  event.target.playVideo();
}
