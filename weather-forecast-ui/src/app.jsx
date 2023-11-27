import './app.css'
import CustomSearch from "./components/customSearch/customSearch.jsx";
import NoResults from "./components/noResults/noResults.jsx";
import {searchState} from "./components/state/searchState.js";
import Results from "./components/results/results.jsx";

const { hasResults, searchClicked, searchResults } = searchState();

export function App() {

    return (
      <div id="mainPage" className="mainPage">
          <div className="container">
              <div id="mainContainer" className="mainContainer">
                  <CustomSearch />
              </div>
              { hasResults.value && ( <Results /> ) }
              { searchClicked.value && !searchResults?.value?.length && (
                      <div className="mainContainer">
                        <NoResults/>
                      </div>
                  )
              }
          </div>
      </div>
  )
}
