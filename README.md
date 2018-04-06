Duplicants created from pigmen
Lure them into the overworld, throw potion made from soul sand onto them
Duplicants know their master, only respond to their master

right click on duplicant to assign tools/armour/inventory, and set name
* can also specify skin to use

Special tool used for creating jobs
* right click block to specify any jobs that are possible

each master has their own list of queued jobs
duplicants will select the first available job that they're capable of doing (for now, everthing) and remove it from the queue

duplicants can also be specifically assigned a job, with optional criteria
* for example, tell a duplicant to tend to an area, but only if the global inventory lacks something
** each master can specifiy inventorys that contribute to the global inventory. duplicants can access these if need be, and use them to determine conditionals

{{ AI }}
All work in the world is handled by a server side AI manager. It maintains a list of all jobs that currently need doing in the world. It also knows about every registered duplicant in the world, where they are, and what they're doing. Based on current needs and priorities (either dictated by the player, or by the various job priorities set) it assigns jobs to available duplicants. 

Duplicants also have the ability to manage their own AI based on the world directly around them. This is used primarily for movement or combat, but actions like gathering things off the ground, and idle animations, are also run through this method

AI manager controls actual important world-wide work, Duplicant controls things more directly related to itself

Jobs code-wise are subclassed from a core Job class. This job class is unique to every task required within the world, and new instances are created as work is required. It maintains tracking of the overall job progress (implementation is job specific) as well as what duplicant is actually performing the work. This allows for a core platform to handle and dispatch things effeciently, and persist state in a central location

Core job class also provides helper methods to simplify movement/intereaction handling per job

Jobs are also created with a priority, which is taken into account when dispatching jobs. Additionally, jobs can be part of a larger group of jobs to be done. This is done by a flag being set on job creation that indicates more work may be required. If this flag is set, when the duplicant finishes the job it was working on, it will check with the job source to see if another job is available. This could be accomplished simply by emitting a new job whenever the old one is finished, but there's a chancec of that duplicant being assigned a new and different job before the continuation of the old one

Once a job is determined to be finished, it is removed from the global list. 

[STEP 1 - DONE]
Duplicant generation process
* Potion from soul sand
* Conversion from pigman to duplicant

[STEP 2 - DONE]
Inventory access/customication
* Setting name, nameplate visibility, skin
* Access to inventory, weapon and armour slots
** Gui, probably based off player inventory, extra buttons in place of crafting grid

INVENTORY DONE, GUI DONE, RENDERING DONE
armour slots are backwards, whoops, and don't check
also doesn't actually counit as them wearing it , no damage / reduction

!! amour slots fixed

Need to finish ability to change name, nameplate visibility, and skin

[STEP 3]

All about AI

Large part is inventory based, based on what it provides, and what is needed
"Farm area requires seeds, provides whatever the outcome"
Colony requires wheat, duplicant collects wheat from farm that provides it

Taking some queues from the original Sims AI. Various objects / designated areas 'advertise' what they provide

Probable jobs
* Inventory (just provides information, duplicant controls process)
** Marked inventory will only broadcast what it contains. Easier, because then it doesnt need to know about what's around it, just trusting that a duplicant will eventually handle it as needed)
* Farming (will emit jobs to be done, rather than providing information about items. More controlling. Knows what's in its boundaries, and what needs to be done. just waits for a duplicant capable of doing the work rewuired)

{{ INVENTORY }}
All blocks have a configurable priority, to allow for fine tuning
Duplicants will only react to whats in range of them (configurable?)

Player can use tool on any block to implement different things
Producer, Consumer, Provider, Storage
* Producer is a combination of a consumer and provider
** Consumer will request items (fuel commonly)
** Provider provides items (farm?, something auto generating)
** Storage will both provide and request any items inside it, or whatever meets it's filter

System allows for flawless interaction with mods, but still allowing for a fine degree of control

!! fluids also a possiblity? duplicant utilizes whatever water storage item it has available
Also needs to be tied to the player somehow? by profile?

Each registered source will emit a job based on its current needs.

{{ world data }}
Holds a map of blockpos -> inventory configuration, pretty simple
Inventory configuration holds a reference to the tile entity, as well as a list of what it takes in, or gives (depending on the configuration)
Producer will accept a configurable list of 'recipes', which specify what it can take in, on what sides, and what it will provide (and where)
Consumer will take a configurable item, on configurable side
Provider will just have a configurable side to pull from, no blacklist/whitelist

Storage will just provide a blacklist/whitelist. It is also a special case, as it doesn't emit any job requests, but simply services as an index for anything requiring item storage