import styles from './searchedItems.module.css';
import {getWeatherAverages} from "../apiCall/apiCall.js";
import {searchState} from "../state/searchState.js";

const { searchClicked, searchResults } = searchState();

const SearchedItems = ({ chips }) => {
    const showSelectedCities = () => {
        return chips.value?.map(c => {
            return (
                <div className={styles.chip}>
                    {c}
                </div>
            )
        });
    }

    const handleSearch = () => {
        if (chips.value?.length) {
            getWeatherAverages(chips.value.join(','));
            searchClicked.value = true;
        }
    }

    const handleClear = () => {
        chips.value = [];
        searchResults.value = [];
        searchClicked.value = false;
    }

    return (
        <div className={styles.displayedData}>
            <div className={styles.chipsContainer}>
                <p>Selected cities:</p>
                <div className={styles.chips}>
                    {!chips?.value?.length
                        ? <span>No cities selected.</span>
                        : showSelectedCities()
                    }
                </div>
                { chips?.value?.length > 0 && (
                    <div className={styles.actionBtns}>
                        <button onClick={handleSearch}>Search</button>
                        <button onClick={handleClear}>Clear</button>
                    </div>
                    ) }
            </div>
        </div>
    )
};

export default SearchedItems;