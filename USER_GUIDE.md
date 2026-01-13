# MovieTheatreCore User Guide

## What MovieTheatreCore does
MovieTheatreCore lets you build in‑game cinemas with multiple screens, schedule shows, and play synchronized video + audio for nearby players. Media downloads, caching, and playback are fully handled by the plugin.

## First‑time setup (recommended order)
1. **Install the plugin** by placing `MovieTheatreCore.jar` in `plugins/`.
2. **Start the server once** so the plugin creates `plugins/MovieTheatreCore/`.
3. **Check dependencies** with `/mtc deps status` (ffmpeg/ffprobe/yt‑dlp/deno).
4. **Create a screen** with `/mtc screen create <name> <width> <height>`.
5. **Add media** with `/mtc media add`.
6. **Play media** with `/mtc play <screen> media <name>`.

> On first run, MovieTheatreCore copies `USER_GUIDE.md` into the plugin folder as a **read‑only** reference file.

## Supported media sources (validated before download)
MovieTheatreCore only accepts real, downloadable media streams. The following sources are supported:

1. **YouTube** (via yt‑dlp)
2. **Direct MP4 / WEBM URLs**
3. **MediaFire direct download links** (must point straight to the file)

If the URL is not a valid downloadable MP4/WEBM stream, MovieTheatreCore will refuse to create the media entry and show a friendly error.

### Examples
- **YouTube**
  ```
  /mtc media add Trailer yt dQw4w9WgXcQ
  ```
- **Direct MP4/WEBM**
  ```
  /mtc media add Intro https://example.com/intro.mp4
  ```
- **MediaFire direct download**
  ```
  /mtc media add Clip https://download.mediafire.com/.../clip.webm
  ```

## Audio (fully automatic)
Audio is **automatic**—no extra commands required.

- When you add media, MovieTheatreCore extracts audio with ffmpeg and converts it into Minecraft‑compatible chunks.
- When you play media on a screen, the audio plays automatically and stays synced to video frames.
- Only players within the screen’s audio range hear it.

> If `audio.enabled` is `false`, video will still play normally without audio.

## Screens & playback
Create and manage screens with `/mtc screen`:

```
/mtc screen create Lobby 6 4
/mtc screen list
/mtc screen delete Lobby
```

Play media on a screen:

```
/mtc play Lobby media Trailer
```

## Admin GUI
Use `/mtc admin` to receive the **MovieTheatreCore Admin Tool**. The item:

- **Cannot be dropped**
- **Right‑click to open the admin GUI**
- Provides quick access to **Media**, **Screens**, **Playback**, and **Settings**
- Requires the `movietheatrecore.admin` permission

## YouTube cookies (tiered model)
MovieTheatreCore follows a realistic, tiered approach to YouTube:

### Tier 1 (default)
- yt‑dlp **without cookies**.
- Works for many videos.
- If blocked, you’ll see a clear error explaining the next steps.

### Tier 2 (optional cookies)
- Place `youtube-cookies.txt` in `plugins/MovieTheatreCore/`.
- The plugin will warn you **before** failure if the cookies are expired.
- Cookie expiration is normal and unavoidable—just re‑export them.

### Tier 3 (fallback when YouTube blocks)
If YouTube is blocked even with cookies:
1. Download the MP4 locally.
2. Upload it to a direct host or MediaFire (direct download link).
3. Add the new URL with `/mtc media add`.

## Common errors & fixes

**“URL must point to a direct MP4/WEBM file.”**
- Use a direct download link or MediaFire direct link (not a share page).

**“YouTube blocked this request without cookies.”**
- Add `youtube-cookies.txt` to the plugin folder and retry.

**“Cookies are expired.”**
- Export fresh cookies and replace the file.

**“Pack URL not configured.”**
- Set `resource_pack.url` or enable the built‑in server (`resource_pack.server.enabled: true`).

**“Media is loading. Try again shortly.”**
- The server is still processing frames/audio. Wait a moment and retry.

## Files created by MovieTheatreCore
All files are under `plugins/MovieTheatreCore/`:

- `configuration/configuration.yml` — Main settings.
- `translations/` — Language files.
- `videos/` — Video metadata and frames.
- `screens/` — Screen definitions.
- `cache/videos/` — Media download cache.
- `resourcepacks/` — Generated audio packs.
- `audio/` — Audio chunks.
- `theatre/` — Theatre rooms & schedules.
- `tmp/` — Temporary files.
