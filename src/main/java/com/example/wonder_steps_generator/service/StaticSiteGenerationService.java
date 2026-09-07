package com.example.wonder_steps_generator.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.wonder_steps_generator.config.WonderStepsProperties;
import com.example.wonder_steps_generator.domain.GeneratedSite;
import com.example.wonder_steps_generator.domain.WonderStep;
import com.example.wonder_steps_generator.mapper.WonderStepDocumentMapper;
import com.example.wonder_steps_generator.recommendation.DatabaseImprovementAdvisor;
import com.example.wonder_steps_generator.render.MyWonderStepsHtmlRenderer;
import com.example.wonder_steps_generator.repository.SpringDataWonderStepRepository;
import com.example.wonder_steps_generator.repository.SpringDataTripRepository;
import com.example.wonder_steps_generator.repository.TripDocument;
import com.example.wonder_steps_generator.repository.WonderStepDocument;

@Service
public class StaticSiteGenerationService {

	private final WonderStepsProperties properties;
	private final SpringDataTripRepository tripRepository;
	private final SpringDataWonderStepRepository repository;
	private final WonderStepDocumentMapper mapper;
	private final MyWonderStepsHtmlRenderer renderer;
	private final DatabaseImprovementAdvisor improvementAdvisor;

	public StaticSiteGenerationService(WonderStepsProperties properties, SpringDataTripRepository tripRepository,
			SpringDataWonderStepRepository repository, WonderStepDocumentMapper mapper, MyWonderStepsHtmlRenderer renderer,
			DatabaseImprovementAdvisor improvementAdvisor) {
		this.properties = properties;
		this.tripRepository = tripRepository;
		this.repository = repository;
		this.mapper = mapper;
		this.renderer = renderer;
		this.improvementAdvisor = improvementAdvisor;
	}

	public GeneratedSite generate() {
		List<WonderStep> steps = findWonderSteps();
		return new GeneratedSite(renderer.render(steps, properties.getStaticSite()), improvementAdvisor.inspect(steps));
	}

	private List<WonderStep> findWonderSteps() {
		List<String> tripIds = findTripIds();
		Sort sort = Sort.by(Sort.Order.asc("order"), Sort.Order.asc("date"), Sort.Order.asc("createdAt"));
		List<WonderStepDocument> documents = tripIds.isEmpty()
				? repository.findAll(sort)
				: repository.findByTripIdIn(tripIds, sort);

		return documents.stream()
				.map(mapper::toWonderStep)
				.filter(step -> !step.title().isBlank())
				.sorted(Comparator.comparing(WonderStep::order).thenComparing(WonderStep::title))
				.toList();
	}

	private List<String> findTripIds() {
		Sort sort = Sort.by(Sort.Order.asc("startDate"), Sort.Order.asc("endDate"));
		String status = properties.getStaticSite().getTripStatus();
		List<TripDocument> trips = properties.getStaticSite().isFeaturedOnly()
				? tripRepository.findByStatusAndFeatured(status, true, sort)
				: tripRepository.findByStatus(status, sort);
		return trips.stream()
				.map(TripDocument::getId)
				.filter(id -> id != null && !id.isBlank())
				.toList();
	}
}
