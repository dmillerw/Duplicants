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

{ duplicant ai cycle }
Look through all nearby sources, looking for any work that needs done (storing items from providers in storage, moving them to consumers asking for them, etc)
If one is found, duplicant will mark it as in progress, preventing other duplicants from taking the job, also providing feedback to the player (somehow, tool?)
Actual manipulation falls down to very simple AI tasks. Walk to source, extract item from inventory, walk to destination, deposit item

{{ world data }}
Holds a map of blockpos -> inventory configuration, pretty simple
Inventory configuration holds a reference to the tile entity, as well as a list of what it takes in, or gives (depending on the configuration)
Storage will just provide a blacklist/whitelist