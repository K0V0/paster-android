package space.kovo.paster.services.sharedPreferencesService;

public interface SharedPreferencesService {

    void save(String key, String data);

    String getString(String key);
}
