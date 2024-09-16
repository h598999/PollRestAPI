<script>
  import { getPollById } from './apiClient.js';
  import { onMount } from 'svelte';  // Import onMount for component lifecycle

  export let currentUser;  // The current user
  export let pollId;       // The poll ID to fetch

  let currentPoll = null;  // The poll object
  let userVote = null;     // The vote the user has cast (if any)

  // Function to fetch the poll by ID
  /** @param{number} id **/
  async function fetchPoll(id) {
    try {
      let poll = await getPollById(id);
      return poll;
    } catch (error) {
    }
  }

  // Fetch the poll when the component mounts
  onMount(async () => {
    currentPoll = await fetchPoll(pollId);

    // Once the poll is fetched, check if the user has already voted
    if (currentPoll && currentUser) {
      userVote = currentUser.votes.find(vote => 
        currentPoll.voteOptions.includes(vote.selected)) || null;
    }
  });

</script>


