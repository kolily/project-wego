package org.zerock.wego.service.review;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.wego.domain.review.ReviewDTO;
import org.zerock.wego.domain.review.ReviewViewVO;
import org.zerock.wego.exception.AccessBlindException;
import org.zerock.wego.exception.NotFoundPageException;
import org.zerock.wego.exception.OperationFailException;
import org.zerock.wego.exception.ServiceException;
import org.zerock.wego.mapper.BadgeGetMapper;
import org.zerock.wego.mapper.ReviewMapper;
import org.zerock.wego.service.badge.BadgeGetService;
import org.zerock.wego.service.common.FileService;
import org.zerock.wego.service.common.ReportService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor

@Service
public class ReviewService {
	
	private final ReviewMapper reviewMapper;
	private final ReportService reportService;
	private final FileService fileService;
	private final BadgeGetService badgeGetService;
	
	
	public List<ReviewViewVO> getList() throws ServiceException {
		log.trace("getList() invoked.");
		
		try {
			return this.reviewMapper.selectAll();
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList
	
	
	public Set<ReviewViewVO> getRandom10List() throws ServiceException {
		log.trace("getRandom10List() invoked.");
		
		try {
			return this.reviewMapper.selectRandom10();
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getList
	
	
//	// 존재여부 
//	public boolean isExist(Integer reviewId) throws ServiceException{
//		
//		return (this.reviewMapper.find(reviewId) != null);
//	}// isExist
	
	// 특정 후기글 조회 
	public ReviewViewVO getById(Integer reviewId) throws Exception {
//		log.trace("getById({}) invoked.", reviewId);	
		
		try {
			ReviewViewVO review = this.reviewMapper.selectById(reviewId);
			
			if(review == null) {
				throw new NotFoundPageException();
			}// if
			
//			if(review.getReportCnt() >= 5) {
//				throw new AccessBlindException();
//			}// if
			
			this.reviewMapper.visitCountUp(reviewId); //조회수증가.
			
			return review;
			
		} catch (NotFoundPageException e) { 
			throw e;
			
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // getById
	
	
	// 특정 후기글 삭제
//	@Transactional // 얘 붙여야되는데 붙이면 삭제가 안됨 ...? 
	public void removeById(Integer reviewId) throws Exception {
//		log.trace("isRemoved({}) invoked.", reviewId);
		
		try {
			boolean isExist = this.reviewMapper.isExist(reviewId);
			
			if(!isExist) { 
				throw new NotFoundPageException();
			}// if
			
			this.reviewMapper.delete(reviewId);
			this.reportService.removeAllByTarget("SAN_REVIEW", reviewId);
			this.fileService.isRemoveByTarget("SAN_REVIEW", reviewId);
			// TO_DO : 좋아요 내역 삭제도 추기돼야 함, 파일 서비스 수정되면 수정해야됨 뱃지도 같이 삭제되어야 할까?
			
			isExist = this.reviewMapper.isExist(reviewId);
			
			if(isExist) {
				throw new OperationFailException();
			}// if
			
		} catch(NotFoundPageException e) {
			throw e;
			
		} catch(OperationFailException e) {
			throw e;
			
		} catch(Exception e) {
			throw new ServiceException(e);
		} // try-catch
	} // remove
	
	
	public void register(ReviewDTO dto) throws Exception {
		log.trace("register({}) invoked.");
		
		try {
			this.reviewMapper.insert(dto);
			
			boolean isExist 
					= this.reviewMapper.isExist(dto.getSanReviewId());
			
			if(!isExist) {
				throw new OperationFailException();
			}// if
			
		}catch(OperationFailException e) { 
			throw e;
			
		} catch (Exception e) {
			throw new ServiceException(e);
			
		} // try-catch
	} // register
	
	
	public void modify(ReviewDTO dto) throws ServiceException {
		log.trace("modify({}) invoked.");
		
		try {
			boolean isExist 
					= this.reviewMapper.isExist(dto.getSanReviewId());
			
			if(!isExist) {
				throw new NotFoundPageException();
			}// if
			
			this.reviewMapper.update(dto);
			
		}catch (NotFoundPageException e) {
			throw e;
			
		} catch (Exception e) {
			throw new ServiceException(e);
			
		} // try-catch
	} // modify

}// end class