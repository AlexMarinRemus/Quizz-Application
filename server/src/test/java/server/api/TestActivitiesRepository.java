package server.api;

import commons.Activity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.ActivitiesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestActivitiesRepository implements ActivitiesRepository {

    public final List<Activity> activities = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();

    private void call(String name) {
        calledMethods.add(name);
    }

    @Override
    public List<Activity> getByConsumption_in_wh(int c) {
        call("getByConsumption_in_wh");
        return findConsumption(c);
    }

//    added for testing. The real repository loads json, nothing gets added manually
    public void add(Activity a){
        activities.add(a);
    }

//    @Override
//    public List<Activity> findActivitiesByConsumption_in_wh(int consumption_in_wh) {
//        call("findConsumption");
//        return findConsumption(consumption_in_wh);
//    }


    @Override
    public List<Activity> findAll() {
        calledMethods.add("findAll");
        return activities;
    }

    @Override
    public List<Activity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Activity> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Activity> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return activities.size();
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Activity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Activity> entities) {

    }

//    Also used exclusively for testing
    @Override
    public void deleteAll() {
        while(!activities.isEmpty()){
            activities.remove(0);
        }
    }

    @Override
    public <S extends Activity> S save(S entity) {
        call("save");
        entity.id = (long) activities.size();
        activities.add(entity);
        return entity;
    }

    @Override
    public <S extends Activity> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Activity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long id) {
        call("existsById");
        return find(id).isPresent();
    }

    private Optional<Activity> find(Long id) {
        return activities.stream().filter(q -> q.id == id).findFirst();
    }

    private List<Activity> findConsumption(int c) {
        List<Activity> a = activities.stream().filter(q -> q.consumption_in_wh == c).collect(Collectors.toList());
        return a;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Activity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Activity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Activity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Activity getOne(Long aLong) {
        return null;
    }

    @Override
    public Activity getById(Long id) {
        call("getById");
        return find(id).get();
    }

    @Override
    public <S extends Activity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Activity> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Activity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Activity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Activity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Activity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Activity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
