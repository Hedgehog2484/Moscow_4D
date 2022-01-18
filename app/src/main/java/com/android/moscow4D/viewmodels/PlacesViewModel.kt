import android.app.Application
import androidx.lifecycle.*
import com.android.moscow4D.database.PlaceEntity
import com.android.moscow4D.database.PlaceRepository
import com.android.moscow4D.database.PlaceRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlacesViewModel(application: Application) : AndroidViewModel(application) {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allPlaces: LiveData<List<PlaceEntity>>
    var allPlacesList: ArrayList<PlaceEntity> = arrayListOf()
    val repository: PlaceRepository

    init {
        val placesDao = PlaceRoomDatabase.getDatabase(application).plaseListDao()
        repository = PlaceRepository(placesDao)
        allPlaces = repository.allPlaces
        initList()
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(place: PlaceEntity) = viewModelScope.launch {
        allPlacesList.add(place)
        repository.insert(place)
    }

    fun get(position: Int): PlaceEntity {
        return allPlacesList[position]
    }

    fun initList() {
        viewModelScope.launch(Dispatchers.IO) {
            allPlacesList = repository.getAllPlacesAsync()
        }
    }
}