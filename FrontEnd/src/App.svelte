<script>
  import PollSelector from './PollSelector.svelte';
  import Poll from './Poll.svelte';
  import PollTemp from './PollTemp.svelte'
  import PollCreator from './PollCreator.svelte';
  import { getUserById } from './apiClient'

  export let currentUser; 
  let currentView = 'pollSelector';  
  let selectedPollId = null;  

  function showPollSelector() {
    currentView = 'pollSelector';  
    selectedPollId = null;
  }

  function showPollCreator() {
    currentView = 'pollCreator';  
    selectedPollId = null;
  }

  async function setCurrentUser(){
      currentUser = await getUserById(0);
      console.log(currentUser);
    }

  /** @param{number} id **/
  function showPoll(id) {
    selectedPollId = id;
    currentView = 'poll';
  }
  setCurrentUser();

</script>

<nav>
  <button on:click={showPollSelector}>Home</button>
  <button on:click={showPollCreator}>Create a new Poll</button>
</nav>

<!-- Conditionally render views based on currentView -->
{#if currentView === 'pollSelector'}
  <PollSelector on:selectPoll={event => showPoll(event.detail)} {currentUser} />
{:else if currentView === 'poll'}
  <!-- Pass the selectedPollId as a prop to the Poll component -->
  <!-- <Poll pollId={selectedPollId} {currentUser}/> -->
  <PollTemp pollId={selectedPollId} {currentUser} />
{:else if currentView === 'pollCreator'}
  <PollCreator {currentUser}/>
{/if}

