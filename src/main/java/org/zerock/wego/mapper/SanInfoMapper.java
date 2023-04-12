package org.zerock.wego.mapper;

import java.util.List;
import java.util.Set;

import org.zerock.wego.domain.SanInfoViewVO;


public interface SanInfoMapper {

	public abstract List<SanInfoViewVO> selectAll(); 
	public abstract Set<SanInfoViewVO> selectRandom10(); 
	public abstract SanInfoViewVO selectById(Integer sanInfoId); 
	public abstract Integer selectSanNameBySanName(String sanName); 
	
} // end interface
