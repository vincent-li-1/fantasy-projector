This is a Java Maven personal project developed by Vincent Li.

All stats are fetched from the sportsdata.io NFL API (https://sportsdata.io/developers/api-documentation/nfl).

The application projects a player's fantasy score for any week since the 2000 season by projecting the player's team's stats against their next opponent based on the team's performance against the most recent three oponents and the next opponent's defensive averages. Projections can be generated for Standard, Half-PPR, or full PPR. At this time, exact player names must be entered to get the correct player stats and thus an accurate projection.

This application runs entirely in terminal.

To run the application, download and install Maven (https://maven.apache.org/). Run `make` to run the application. A `.env` file is required in the root directory with the key-value

```
API_KEY: <API KEY FROM sportsdata.io>
```
