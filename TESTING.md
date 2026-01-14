# Testing

## Acceptance checks

1. Create a screen, add media, play it (video shows without resource pack):
   ```sh
   /mtc screen create Lobby 6 4
   /mtc media add Trailer https://example.com/intro.mp4
   /mtc play Lobby Trailer
   ```

2. Audio pack auto-applies within radius (never 0.0.0.0):
   ```sh
   /mtc pack status
   /mtc debug pack
   ```
   Then walk into the screen radius and confirm the client downloads from:
   ```
   https://pack.node4.b0x.us/pack.zip
   ```

3. Walk out of radius, then back in (no relog, no spam re-download):
   ```sh
   # Move outside the radius, wait 5s, move back in.
   ```

4. Run 3 screens playing different media (closest audio wins):
   ```sh
   /mtc screen create ScreenA 4 3
   /mtc screen create ScreenB 4 3
   /mtc screen create ScreenC 4 3
   /mtc media add ClipA https://example.com/a.mp4
   /mtc media add ClipB https://example.com/b.mp4
   /mtc media add ClipC https://example.com/c.mp4
   /mtc play ScreenA ClipA
   /mtc play ScreenB ClipB
   /mtc play ScreenC ClipC
   ```

5. Delete a screen safely (removes all frames/configs, no exceptions):
   ```sh
   /mtc screen delete Lobby
   ```
