package fr.xxathyx.mediaplayer.media;

import fr.xxathyx.mediaplayer.playback.PlaybackOptions;
import fr.xxathyx.mediaplayer.video.Video;

public class MediaPlayback {

    private final Video video;
    private final PlaybackOptions options;
    private final MediaEntry entry;

    public MediaPlayback(Video video, PlaybackOptions options, MediaEntry entry) {
        this.video = video;
        this.options = options;
        this.entry = entry;
    }

    public Video getVideo() {
        return video;
    }

    public PlaybackOptions getOptions() {
        return options;
    }

    public MediaEntry getEntry() {
        return entry;
    }
}
