package kr.or.wds.project.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.or.wds.project.dto.request.PortfoliosRequest;
import kr.or.wds.project.entity.PortfolioEntity;
import kr.or.wds.project.service.PortfolioService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/portfolios")  
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping
    public ResponseEntity<List<PortfolioEntity>> getPortfolios() {
        return ResponseEntity.ok(portfolioService.getPortfolios());
    }

    @PostMapping
    public ResponseEntity<Void> createPortfolio(@RequestBody PortfoliosRequest request) {
        portfolioService.createPortfolio(request);
        return ResponseEntity.ok().build();
    }
}
