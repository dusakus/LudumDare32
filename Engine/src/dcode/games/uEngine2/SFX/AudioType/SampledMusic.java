package dcode.games.uEngine2.SFX.AudioType;

import dcode.games.uEngine2.StData;

/**
 * Created by dusakus on 13.04.15.
 */
class SampledMusic {
    public float volume = 1;
    public float pan = 0;
    private boolean isPlaying = false;
    private boolean isStopped = false;
    private boolean isLooped = false;
    private String msPlayerID;
    private boolean wasPlaying = false;
    private boolean wasStopped = false;
    private boolean wasLooped = false;
    private float density = -1;      //Because reasons

    //Check if changed, and update backend if needed
    public void updateStatus() {
        if (msPlayerID == null) return;                  //return if id not specified
        if (isPlaying != wasPlaying) {
            StData.resources.smsx.setPlaying(msPlayerID, isPlaying, isLooped);
            wasPlaying = isPlaying;
        }
        if (isStopped != wasStopped) {
            StData.resources.smsx.setStopped(msPlayerID, isPlaying);
            wasStopped = isStopped;
        }
        if (isLooped != wasLooped) {
            StData.resources.smsx.setStopped(msPlayerID, isPlaying);
            wasLooped = isLooped;
        }
    }
}
