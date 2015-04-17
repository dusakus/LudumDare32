/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2;

/**
 * @author dusakus
 */
public class uGameSetup {

	//Debug mode
	public boolean debug = false;                            //enable debug output and features

	//Game name
	public String safeName = "uGame_unnamed";                //Safe name, will be used in file storage
	public String screenName = "UNNAMED";                    //Will be used in messages and generic renderers
	public String windowTitle = "Unnamed uGame";            //You guessed it, will be used as window title, a suffix will be added " | in DCode uEngine 2.[ver] r[rel]"

	// Basic Render information
	public int width = 320;                                //Width of internal canvas
	public int height = 240;                                //Height of internal canvas
	public int scale = 3;                                    //Scalig applied to internal canvas

	// Extended Render information
	public int postProcCount = 4;                            //number of aviable postProcessing Sockets

	// Speed settings
	public int FPS = 60;                                    //Render thread target speed
	public int TPS_logic = 60;                            //MAIN Logic thread target speed, if less than 1 logic tasks will be tied into render thread
	public int TPS_MSX = 0;                                //Game Audio steps per second
	public int TPS_BG = 60;                                //Background logic thread target speed, if 0 background tasks will be disabled, if -1 they will run without limit, if less than -1 they will be tied to render thread

	// Sprite system settings
	public int spriteTableSize = 64;                        //Size of the main sprite table
	public int spriteLayerSize = 16;                        //Sprite Count per layer
	public boolean enableSpriteWrappers = true;             //enable SpriteWrappers

	// Translation module settings
	public boolean enableTranslator = false;                //If the translation module should be enabled, will BORK if default lang doesn't exist
	public String defaultLangId = "baseLang";                //the default language, file MUST EXIST

	// Sound system settings               					//All disabled by default
	public boolean soundEnabled = false;
	public int wavChannelCount = 0;                            //how many WAV playback channels are needed
	public int scriptChannelCount = 0;                        //how many SoundScript playback channels are needed
	public int effectChannelCount = 0;                      //how many effect (short WAV) playback channels are needed
	public int effectTableSize = 0;                            //size of the effect table
	public int generatorChannelCount = 0;                    //how many sound generators should be created

	// Fonts
	public int stringBufferSize = 16;
}
