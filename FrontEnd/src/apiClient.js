// apiClient.js 

  const baseUri = 'http://localhost:8080/api/v1'

  /**@param{number} id **/
  export async function getUserById(id){
    const response = await fetch(baseUri + '/users/' + id);
    return await response.json();
  }

  export async function fetchPolls(){
    const response = await fetch(baseUri + '/polls');
    return await response.json();
  }

  /** @param{number} id **/ 
  export async function getPollById(id){
    const response = await fetch(baseUri + '/polls/' + id);
    return await response.json();
  }

  /**
  * @param{Object} voteData 
  * @returns{Promise<Object>}
  */
  export async function createVote(voteData) {
    const response = await fetch(baseUri + '/votes/0',{
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(voteData),
    });
    if (!response.ok){
      const error = await response.json();
      throw new Error(`Error creating vote: ${error.message}`);
    }
    return response.json();
  }

  
