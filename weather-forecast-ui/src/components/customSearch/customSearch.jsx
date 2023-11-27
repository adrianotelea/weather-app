import cities from './cities.json';
import styles from './customSearch.module.css';
import { signal } from '@preact/signals';
import SearchedItems from "../searchedItems/searchedItems.jsx";

const searchTerm = signal('');
const suggestions = signal([]);
const chips = signal([]);

const CustomSearch = () => {
    const onInputChange = (e) => {
        searchTerm.value = e.target.value;
        updateSuggestions();
    };

    const updateSuggestions = () => {
        if (searchTerm.value.length > 0) {
            const regex = new RegExp(`^${searchTerm.value}`, 'i');
            suggestions.value = cities.filter(city => regex.test(city.name));
        } else {
            suggestions.value = [];
        }
    };

    const addSearchTerm = (suggestionName) => {
        chips.value = [...chips.value, suggestionName];
        suggestions.value = [];
        searchTerm.value = '';
    }

    return (
        <div id="searchContainer" className={styles.searchContainer}>
            <div className={styles.leftSection}>
                <div className={styles.availableData}>
                    <p>Data is available for:</p>
                    <span>Cluj-Napoca, Bucuresti, Timisoara, Constanta, Baia-Mare, Arad</span>
                </div>
                <p>Select cities:</p>
                <div className={styles.search}>
                    <input
                        type="text"
                        value={searchTerm.value}
                        onInput={onInputChange}
                        className={styles.searchInput}
                    />
                    {suggestions.value.length > 0 && (
                        <ul className={styles.dropdown}>
                            {suggestions.value.map((suggestion, index) => (
                                <li key={index}
                                    className={styles.item}
                                    onClick={(e) => addSearchTerm(suggestion.name)}>
                                        <span>{suggestion.name.substring(0, searchTerm.value.length)}</span>
                                        <span>{suggestion.name.substring(searchTerm.value.length)}</span>
                                </li>
                            ))}
                        </ul>
                    )}
                </div>
            </div>
            <SearchedItems chips={chips}/>
        </div>
    );
};

export default CustomSearch;
