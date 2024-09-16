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
  * @param{number} userid 
  * @returns{Promise<Object>}
  */
  export async function createVote(userid, voteData) {
    const response = await fetch(baseUri + '/votes/' + userid,{
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

  /**
  * @param{Object} voteData 
  * @param{number} voteid 
  * @returns{Promise<Object>}
  */
  export async function updateVote(voteid, voteData) {
    console.log(voteid);
    const response = await fetch(baseUri + '/votes/' + voteid,{
      method: 'PUT',
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

  /**
  * @param{Object} Poll 
  * @param{number} userid 
  * @returns{Promise<Object>}
  */
  export async function createPoll(userid, Poll) {
    const response = await fetch(baseUri + '/polls/' + userid,{
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(Poll),
    });
    if (!response.ok){
      const error = await response.json();
      throw new Error(`Error creating poll: ${error.message}`);
    }
    return response.json();
  }
