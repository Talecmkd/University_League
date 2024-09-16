package mk.ukim.finki.wp.liga.service.football.impl;

import mk.ukim.finki.wp.liga.model.Exceptions.InvalidFootballProductException;
import mk.ukim.finki.wp.liga.model.FootballTeam;
import mk.ukim.finki.wp.liga.model.shop.FootballProduct;
import mk.ukim.finki.wp.liga.repository.football.FootballProductRepository;
import mk.ukim.finki.wp.liga.repository.football.FootballTeamRepository;
import mk.ukim.finki.wp.liga.service.football.FootballProductService;
import mk.ukim.finki.wp.liga.service.football.FootballTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FootballProductServiceImpl implements FootballProductService {

    private final FootballProductRepository footballProductRepository;
    private final FootballTeamRepository footballTeamRepository;

    public FootballProductServiceImpl(FootballProductRepository footballProductRepository, FootballTeamRepository footballTeamRepository) {
        this.footballProductRepository = footballProductRepository;
        this.footballTeamRepository = footballTeamRepository;
    }

    @Override
    @Cacheable("football-products_by_team")
    public List<FootballProduct> findByTeam(FootballTeam team) {
        return footballProductRepository.findByFootballMerch(team);
    }

    @Override
    @Cacheable("football-products")
    public List<FootballProduct> findAll() {
        return footballProductRepository.findAll();
    }

    @Override
    public List<FootballProduct> findByTeamId(Long teamId) {
        return footballProductRepository.findById(teamId).stream().toList();
    }

    @Override
    public FootballProduct findById(Long id) {
        return footballProductRepository.findById(id).orElseThrow(InvalidFootballProductException::new);
    }


    @Override
    public void deleteById(Long id) {
        footballProductRepository.deleteById(id);
    }

    @Override
    public void createNewFootballProduct(String name, String description, double price, String imageUrl, Long teamId) {
        FootballTeam team = footballTeamRepository.findById(teamId).orElseThrow(InvalidFootballProductException::new);
        FootballProduct product = new FootballProduct(name, description, price, imageUrl, team);
        footballProductRepository.save(product);
    }

    @Override
    public void update(Long id, String name, String description, double price, String imageUrl, Long teamId) {
        FootballProduct product = findById(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImage(imageUrl);
        FootballTeam team = footballTeamRepository.findById(teamId).orElseThrow(InvalidFootballProductException::new);
        product.setFootballMerch(team);
        footballProductRepository.save(product);
    }

    @Override
    public List<FootballProduct> findByTeamName(String teamName) {
        return footballProductRepository.findByFootballMerch_TeamName(teamName);
    }

}
