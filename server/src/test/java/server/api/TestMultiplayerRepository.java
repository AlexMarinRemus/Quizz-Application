package server.api;

import commons.Multiplayer;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.MultiplayerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestMultiplayerRepository implements MultiplayerRepository {

    public final List<Multiplayer> players = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();

    private void call(String name) {
        calledMethods.add(name);
    }

    @Override
    public List<Multiplayer> findAll() {
        call("findAll");
        return players;
    }

    @Override
    public List<Multiplayer> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Multiplayer> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Multiplayer> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        call("count");
        return players.size();
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Multiplayer entity) {
        call("delete");
        players.remove(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Multiplayer> entities) {

    }

    @Override
    public void deleteAll() {
        call("deleteAll");
        if(!players.isEmpty()){
            while(!players.isEmpty()){
                players.remove(0);
            }
        }
    }

    @Override
    public <S extends Multiplayer> S save(S entity) {
        call("save");
        entity.id = (long) players.size();
        players.add(entity);
        return entity;
    }

    @Override
    public <S extends Multiplayer> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Multiplayer> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long id) {
        call("existsById");
        return find(id).isPresent();
    }

    private Optional<Multiplayer> find(Long id) {
        return players.stream().filter(q -> q.id == id).findFirst();
    }
    private List<Multiplayer> findByGameID(Long id) {return players.stream().filter(q -> q.gameID == id).collect(Collectors.toList());}

    @Override
    public void flush() {

    }

    @Override
    public <S extends Multiplayer> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Multiplayer> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Multiplayer> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Multiplayer getOne(Long aLong) {
        return null;
    }

    @Override
    public Multiplayer getById(Long id) {
        call("getById");
        return find(id).get();
    }

    @Override
    public <S extends Multiplayer> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Multiplayer> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Multiplayer> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Multiplayer> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Multiplayer> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Multiplayer> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Multiplayer, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Multiplayer> getByGameID(long id) {
        call("getByGameID");
        return findByGameID(id);
    }

}
