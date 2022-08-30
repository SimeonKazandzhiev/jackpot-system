# Jackpot System Project

# Technological Stack

Java 18,
Spring,
MySQL,
JDBI Declarative,
WebSockets,
Plain Java Synchronization,
Junit,
Mockito,
Maven


# Jackpot configuration

Each jackpot is defined for a casino operator. The casino operator is the entity that is 
responsible for managing the players, bets and jackpots. For example: WinBet, Efbet, Palmsbet. 
There can only be one jackpot per operator. Each jackpot consists of different levels. Each level 
accumulates money and can be won by the player. It has a separate configuration for the 
following attributes: 

● Percentage of the bet that goes to the level - If a player bets 10 and the percentage is 
0.1%, then 0.01 is added to the level’s jackpot amount. 

● Starting amount for the level - the default amount of the jackpot for the specific level. 
This is the amount that the jackpot will always start from, from example: 100. After a 
level is won, the amount in the level should go back to this amount. 

● Minimum amount to be won - shows what should be the accumulated amount of the 
jackpot in order for it to be won by the user. For example, if the value is 200, then no 
player can win this level of the jackpot, unless it is equal or more than 200. 

● Win probability - shows what is the probability of winning this particular jackpot level. 
Please note that the sum of all win probabilities for all levels in a jackpot should amount 
to 100 

Also, a jackpot for an operator has these two global settings: 
● Operator Identifier 
● Jackpot trigger percentage - this is the probability to trigger a jackpot. For example, if this 
is set to 5, then approximately 5% of all bets will trigger the jackpot. 
● Number of Levels - this is how many levels the jackpot will have 

Also, a jackpot for an operator has these two global settings: 
● Operator Identifier 
● Jackpot trigger percentage - this is the probability to trigger a jackpot. For example, if this 
is set to 5, then approximately 5% of all bets will trigger the jackpot. 
● Number of Levels - this is how many levels the jackpot will have 


# Bet placement 

Let the player start with 100000 in terms of money and be able to perform virtual bets. We are 
not interested if the bet is winning or losing, our only interest is in how this bet affects the 
jackpot. Let’s assume that there is a separate system that manages the bets depending on the 
slot game and that this system sends the bet information to a RESTFul service of our jackpot. 

The data sent will be in JSON format and have the following attributes: 
● Bet Amount
● Player Identifier 
● Operator Identifier


# Workflow 

Each time bet is placed, the service reads the configuration of the jackpot (if any) and reads 
all configurations for all the levels. Let’s assume that there are 4 levels and they have 
respectively the percentage of the bet as follows: L1: 0.1%, L2: 0.2%, L3: 0.3%, L4: 0.4%. Let’s 
also assume that the bet amount is 10. The following amounts will be deducted from the bet and 
placed into the different jackpot levels for the given operator: 
Level 1: +0.01 
Level 2: +0.02 
Level 3: +0.03 
Level 4: +0.04 
The workflow of winning any of the jackpot levels starts with checking the Jackpot trigger 
percentage upon every bet. If the percentage is met, then the next step is to collect information 
for each level’s Win probability and decide at random which jackpot level should be checked 
further for a winning condition. For example, let’s have 4 levels with the following win 
probabilities: 50%, 30%, 15%, 5%. The system generates a percentage at random and it hits 
Level 1, being the most probable. The final step that decides if the jackpot will be paid to the 
player is if the Minimum amount to be won property has been met. If this setting is set to 200 for 
Level 1 and if the current level amount is currently at 192.04, then the jackpot level is not 
reached and there is no payout to the player. But if the current amount is at 210.48, then the 
total sum is paid to the player and his balance is adjusted.
