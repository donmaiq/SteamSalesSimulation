# steamsimulation
Java project created by Joona, Jonnie, and Joni  
There's a private repo for other group projects and documents (Can be accessed by the group members):  
https://github.com/MacsyMetropolia/OliotRyhma4


## General plan
- simulate 1 year of steam sales
- unique users with varied behaviour patterns

### Structure
- Game class, one game in the steam store
- user class, one individual steam user
- behaviour class, the behaviour of a user or a game, chances of sales, price drops etc.

#### User class
- string name
- double disposable money, increased at regular intervals
- array[] owned games, users will not buy duplicates
- behaviour = new behaviour(random user behaviour)

#### Game class
- string name
- double price
- int review(0-100), scale of how good the game is, higher = more word of mouth
- double $ marketing, scale of how much the game is marketed, higher = wider potential audience
- double $ revenue
- double $ steam cut earning
- behaviour = new behaviour(random game behaviour)

#### Behaviour class
##### user behaviour
- interest genre, some kind of spectrum? ex. fps,casual,rpg(0.1, 0.4, 0.8)
- hype scale 0.0-1.0, higher is bigger chance to buy new big releases of interesting genre
- variation scale 0.0-1.0, higher means user likes to play larger variety of games

##### game behaviour
- sale rating 0.0-1.0, higher means more often on sale
- sale scale [25%,45%,60%], most common sale amounts
- price drop timescale, how long it should take before game drops in price
- price drop amount %, how much game price drops

##todo
-algorithymes to calculate everything, size of audience(review,marketing) etc
