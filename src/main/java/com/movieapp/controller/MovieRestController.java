package com.movieapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.movieapp.entity.MovieEntity;
import com.movieapp.model.MovieTO;
import com.movieapp.response.ResponseMessage;
import com.movieapp.service.BaseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/ws/movies")
@Api(value = "movies", description = "Operations to add remove movie")
public class MovieRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MovieRestController.class);

	@Autowired
	private BaseService<MovieTO> movieService;

	/**
	 * End point to create new resource
	 * 
	 * @param movie
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "Create new Movie")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Created"),
			@ApiResponse(code = 400, message = "Movie not saved") })
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap saveMovie(@RequestBody @NotNull MovieTO movie, HttpServletRequest request) {

		LOGGER.info(" ===============Save new Record ===========");
		ResponseMessage responseMessage = null;

		if (movie != null) {
			if (!verifyRating(movie.getRating())) {
				responseMessage = new ResponseMessage("saveMovie", 400, "NOT SAVED",
						"Incorrect rating, Should be in rang( 0.5 to 5.0)", movie);
				return new ModelMap("response", responseMessage);
			}
		}

		MovieTO movieTO = movieService.save(movie);
		if (movieTO != null) {
			LOGGER.debug("movie save successfully  :: " + movieTO);
			responseMessage = new ResponseMessage("saveMovie", 200, "MOVIE SAVE SUCCESSFULLY", "OK", movieTO);
			return new ModelMap("response", responseMessage);
		}
		responseMessage = new ResponseMessage("saveMovie", 400, "NOT SAVED", "RECORD NOT SAVED", movieTO);
		return new ModelMap("response", responseMessage);

	}

	/**
	 * End point to Delete movie
	 * 
	 * @param movie
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "Remove Movie")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Deleted"),
			@ApiResponse(code = 400, message = "Movie not Deleted") })

	@RequestMapping(value = "/remove", method = RequestMethod.DELETE)
	@ResponseBody
	public ModelMap deleteMovie(@RequestBody @NotNull MovieTO movie, HttpServletRequest request) {
		LOGGER.info(" ===============Delete Movie===========");
		ResponseMessage responseMessage = null;

		if (movie.getId() != null) {
			try {
				MovieTO entity = movieService.getMovie(movie.getId());
				if (entity == null) {
					responseMessage = new ResponseMessage("deleteMovie", 400, "IN CORRECT MOVIE ID", "ID NOT FOUND",
							movie);
					return new ModelMap("response", responseMessage);
				}
			} catch (Exception e) {
				responseMessage = new ResponseMessage("deleteMovie", 400, "IN CORRECT MOVIE ID", "ID NOT FOUND", movie);
				return new ModelMap("response", responseMessage);
			}
		} else {
			responseMessage = new ResponseMessage("deleteMovie", 400, "IN CORRECT MOVIE ID", "ID NOT FOUND", movie);
			return new ModelMap("response", responseMessage);
		}

		boolean status = movieService.delete(movie);
		if (status) {
			LOGGER.debug("movie delete successfully  :: " + movie);
			responseMessage = new ResponseMessage("deleteMovie", 200, "DELETE SUCCESSFULLY", "OK", movie);
		} else {
			responseMessage = new ResponseMessage("deleteMovie", 400, "NOT DELETE", "RECORD NOT DELETE", movie);
		}
		return new ModelMap("response", responseMessage);

	}

	/**
	 * End point to update movie resource
	 * 
	 * @param movie
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "Update Movie")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated"),
			@ApiResponse(code = 400, message = "Movie not Updated") })

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	@ResponseBody
	public ModelMap updateMovie(@RequestBody @NotNull MovieTO movie, HttpServletRequest request) {
		LOGGER.info(" ===============Update Movie ===========");
		ResponseMessage responseMessage = null;

		if (movie.getId() != null) {
			try {
				MovieTO entity = movieService.getMovie(movie.getId());
				if (entity == null) {
					responseMessage = new ResponseMessage("updateMovie", 400, "IN CORRECT MOVIE ID", "ID NOT FOUND",
							movie);
					return new ModelMap("response", responseMessage);
				}
			} catch (Exception e) {
				responseMessage = new ResponseMessage("updateMovie", 400, "IN CORRECT MOVIE ID", "ID NOT FOUND", movie);
				return new ModelMap("response", responseMessage);
			}
		} else {
			responseMessage = new ResponseMessage("updateMovie", 400, "IN CORRECT MOVIE ID", "ID NOT FOUND", movie);
			return new ModelMap("response", responseMessage);
		}

		if (!verifyRating(movie.getRating())) {
			responseMessage = new ResponseMessage("updateMovie", 400, "NOT UPDATED",
					"Incorrect rating, Should be in rang( 0.5 to 5.0)", movie);
			return new ModelMap("response", responseMessage);
		}

		MovieTO movieTo = movieService.update(movie);
		if (movieTo != null) {
			LOGGER.debug("movie update successfully  :: " + movie);
			responseMessage = new ResponseMessage("updateMovie", 200, "UPDATED SUCCESSFULLY", "OK", movie);
		} else {
			responseMessage = new ResponseMessage("updateMovie", 400, "MOVIE NOT UPDATED", "RECORD NOT UPDATED", movie);
		}
		return new ModelMap("response", responseMessage);

	}

	/**
	 * End point to get all resource
	 * 
	 * @return
	 */
	@ApiOperation(value = "Get all movies")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully read all movies"),
			@ApiResponse(code = 400, message = "Not able to fatch movies") })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public ModelMap movies() {
		LOGGER.info(" in side movies ::--->");

		ResponseMessage responseMessage = null;
		List<MovieTO> movisList = movieService.getAll();

		if (movisList != null && movisList.size() > 0) {
			LOGGER.debug("Movies read successfully  :: " + movisList);
			responseMessage = new ResponseMessage("movies", 200, "OK", "GET ALL MOVIES", movisList);
			return new ModelMap("response", responseMessage);
		}

		responseMessage = new ResponseMessage("movies", 400, "NO RECORD FOUND", "NOT FOUND", movisList);
		return new ModelMap("response", responseMessage);
	}

	/**
	 * End point to get all resource
	 * 
	 * @return
	 */
	@ApiOperation(value = "Get movies by ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully read movies"),
			@ApiResponse(code = 400, message = "Not able to fatch movies") })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ModelMap moviesById(@PathVariable("id") int id) {
		LOGGER.info(" in side moviesById ::--->");
		ResponseMessage responseMessage = null;

		MovieTO movieTO = movieService.getMovie(id);

		if (movieTO != null) {
			LOGGER.debug("Movies read successfully  :: " + movieTO);
			responseMessage = new ResponseMessage("moviesById", 200, "OK", "GET MOVIES BY ID", movieTO);
			return new ModelMap("response", responseMessage);
		}

		responseMessage = new ResponseMessage("moviesById", 400, "NO RECORD FOUND", "NOT FOUND", movieTO);
		return new ModelMap("response", responseMessage);
	}

	/**
	 * Method to verify the rating
	 * 
	 * @param rating
	 * @return
	 */
	private boolean verifyRating(double rating) {
		return ((rating >= 0.5) && (rating <= 5.0));
	}
}
