package es.ugr.osgiliart;

import java.util.ArrayList;

import es.ugr.osgiliart.core.generators.RandomFloatGenerator;
import es.ugr.osgiliart.core.generators.color.ColorGenerator;
import es.ugr.osgiliart.core.generators.color.RandomColorGenerator;
import es.ugr.osgiliart.core.generators.point.RandomPointGenerator;
import es.ugr.osgiliart.core.rand.RandU;
import es.ugr.osgiliart.core.rand.Randomizer;
import es.ugr.osgiliart.primitives.Primitive;
import es.ugr.osgiliart.primitives.basic.generators.CircleGenerator;
import es.ugr.osgiliath.OsgiliathService;
import es.ugr.osgiliath.evolutionary.elements.EvolutionaryBasicParameters;
import es.ugr.osgiliath.evolutionary.elements.FitnessCalculator;
import es.ugr.osgiliath.evolutionary.individual.Individual;
import es.ugr.osgiliath.evolutionary.individual.Initializer;

public class ArtisticInitializer extends OsgiliathService implements Initializer {
	
	private FitnessCalculator fitnessCalculator;
	
	@Override
	public ArrayList<Individual> initializeIndividuals(int size) {
		ArrayList<Individual> individuals = new ArrayList<Individual>();		
		for ( int i = 0; i < size; ++i) {
			individuals.add( newIndividual() );
		}
		
		/*
		 * Calculate fitness for all 
		 */
		int id = 0;
		for ( Individual individual: individuals ) {		
			((ArtisticIndividual) individual).setGeneration(0);
			((ArtisticIndividual) individual).setUniqId(id++);
			individual.setFitness( fitnessCalculator.calculateFitness(individual) );
		}
		return individuals;
	}
	
	public void setFitnessCalculator(FitnessCalculator fitnessCalculator){
		this.fitnessCalculator = fitnessCalculator;
	}
	
	public void unsetFitnessCalculator(FitnessCalculator fitnessCalculator){
		this.fitnessCalculator = null;
	}
	

	protected ArtisticIndividual newIndividual () {		
		ArtisticIndividual individual = new ArtisticIndividual();
		ArtisticGenome genome = new ArtisticGenome();
		ArrayList<Primitive> primitives = new ArrayList<Primitive>();
		
		/* random numbers generators */
		RandU randR = new RandU(); 				//
		RandU randG = new RandU();		//
		RandU randB = new RandU(); 	//				
		RandU randRadius  = new RandU(0.01f,1.0f);		
		RandU randPoint = new RandU();
		
		RandomFloatGenerator radiusGenerator = new RandomFloatGenerator(randRadius);
		RandomPointGenerator pointGenerator = new RandomPointGenerator(randPoint);
		ColorGenerator       colorGenerator = new RandomColorGenerator(randR,randG,randB);
		
		CircleGenerator circleGenerator = new CircleGenerator(radiusGenerator, pointGenerator, colorGenerator);
		

		int N = (Integer) this.getAlgorithmParameters().getParameter(ArtisticParameters.GENOME_SIZE);
		for ( int i = 0; i < N; ++i ) {
			primitives.add( circleGenerator.generate() );
		}
		
		genome.setPrimitives(primitives);
		individual.setGenome(genome);		
		return individual;
		
	}
	
}
